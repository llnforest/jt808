package org.yzh.web.controller;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectModel;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.arcsoft.face.toolkit.ImageInfoEx;
import org.yzh.web.commons.config.FaceConfig;
import org.yzh.web.model.entity.CzJsTrainImgs;
import org.yzh.web.service.CzJsDevicesService;
import org.yzh.web.service.CzJsTrainImgsService;
import org.yzh.web.service.CzPlatConfigTimeChecksService;

import javax.annotation.Resource;
import javax.annotation.Resources;

import static com.arcsoft.face.toolkit.ImageFactory.getGrayData;
import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

@Controller
@RequestMapping
@Slf4j
public class FaceController {

    @Autowired
    private CzJsTrainImgsService czJsTrainImgsService;

    @Autowired
    private CzPlatConfigTimeChecksService czPlatConfigTimeChecksService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CzJsDevicesService czJsDevicesService;

    @RequestMapping(value = "api/face/similar", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String similar(@RequestParam(value = "imgId") int imgId,@RequestParam(value = "checkType") int checkType){
        List<String> imgList = czJsTrainImgsService.getImgList(imgId);
        if(imgList.size() != 2){
            czJsTrainImgsService.failCheck(imgId,checkType);
            return "{\"status\":1,\"data\":null,\"message\":\"图片缺失\"}";
        }
        FaceEngine faceEngine = new FaceEngine(FaceConfig.libPath);
        //激活引擎
        int errorCode = faceEngine.activeOnline(FaceConfig.appId, FaceConfig.sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            log.error("引擎激活失败{}",errorCode);
            //引擎卸载
            errorCode = faceEngine.unInit();
            return "{\"status\":1,\"data\":null,\"message\":\"引擎激活失败\"}";
        }


        ActiveFileInfo activeFileInfo=new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            log.error("获取激活文件信息失败");
            //引擎卸载
            errorCode = faceEngine.unInit();
            return "{\"status\":1,\"data\":null,\"message\":\"获取激活文件信息失败\"}";
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);


        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            log.error("初始化引擎失败");
            //引擎卸载
            errorCode = faceEngine.unInit();
            return "{\"status\":1,\"data\":null,\"message\":\"初始化引擎失败\"}";
        }


        //人脸检测
        ImageInfo imageInfo = getRGBData(new File(FaceConfig.prePath+imgList.get(0)));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        if(faceInfoList.size() == 0){
            czJsTrainImgsService.failCheck(imgId,checkType);
            //引擎卸载
            errorCode = faceEngine.unInit();
            return "{\"status\":1,\"data\":null,\"message\":\"未检测出人脸特征\"}";
        }
        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
//        log.info("1特征值大小：" + faceFeature.getFeatureData().length);

        //人脸检测2
        ImageInfo imageInfo2 = getRGBData(new File(FaceConfig.prePath+imgList.get(1)));
        List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(),imageInfo2.getImageFormat(), faceInfoList2);
//        System.out.println(faceInfoList2);
        if(faceInfoList2.size() == 0){
            czJsTrainImgsService.failCheck(imgId,checkType);
            //引擎卸载
            errorCode = faceEngine.unInit();
            return "{\"status\":1,\"data\":null,\"message\":\"未检测出人脸特征\"}";
        }
        //特征提取2
        FaceFeature faceFeature2 = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo2.getImageFormat(), faceInfoList2.get(0), faceFeature2);
//        log.info("2特征值大小：" + faceFeature2.getFeatureData().length);

        //特征比对
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
        FaceSimilar faceSimilar = new FaceSimilar();

        errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);

//        log.info("相似度：" + faceSimilar.getScore());

//        //设置活体测试
//        errorCode = faceEngine.setLivenessParam(0.5f, 0.7f);
//        //人脸属性检测
//        FunctionConfiguration configuration = new FunctionConfiguration();
//        configuration.setSupportAge(true);
//        configuration.setSupportFace3dAngle(true);
//        configuration.setSupportGender(true);
//        configuration.setSupportLiveness(true);
//        errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);
//
//
//        //性别检测
//        List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
//        errorCode = faceEngine.getGender(genderInfoList);
//        log.info("性别：" + genderInfoList.get(0).getGender());
//
//        //年龄检测
//        List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
//        errorCode = faceEngine.getAge(ageInfoList);
//        System.out.println("年龄：" + ageInfoList.get(0).getAge());
//
//        //3D信息检测
//        List<Face3DAngle> face3DAngleList = new ArrayList<Face3DAngle>();
//        errorCode = faceEngine.getFace3DAngle(face3DAngleList);
//        log.info("3D角度：" + face3DAngleList.get(0).getPitch() + "," + face3DAngleList.get(0).getRoll() + "," + face3DAngleList.get(0).getYaw());
//
//        //活体检测
//        List<LivenessInfo> livenessInfoList = new ArrayList<LivenessInfo>();
//        errorCode = faceEngine.getLiveness(livenessInfoList);
//        log.info("结果：{}",errorCode);
//        log.info("活体：" + livenessInfoList.get(0).getLiveness());
//
//
//        //IR属性处理
//        ImageInfo imageInfoGray = getGrayData(new File("C:\\Users\\Star\\Desktop\\image\\2.jpg"));
//        List<FaceInfo> faceInfoListGray = new ArrayList<FaceInfo>();
//        errorCode = faceEngine.detectFaces(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray);
//
//        FunctionConfiguration configuration2 = new FunctionConfiguration();
//        configuration2.setSupportIRLiveness(true);
//        errorCode = faceEngine.processIr(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray, configuration2);
//        //IR活体检测
//        List<IrLivenessInfo> irLivenessInfo = new ArrayList<>();
//        errorCode = faceEngine.getLivenessIr(irLivenessInfo);
//        log.info("结果：{}",errorCode);
//        log.info("IR活体：" + irLivenessInfo.get(0).getLiveness());
//
//        ImageInfoEx imageInfoEx = new ImageInfoEx();
//        imageInfoEx.setHeight(imageInfo.getHeight());
//        imageInfoEx.setWidth(imageInfo.getWidth());
//        imageInfoEx.setImageFormat(imageInfo.getImageFormat());
//        imageInfoEx.setImageDataPlanes(new byte[][]{imageInfo.getImageData()});
//        imageInfoEx.setImageStrides(new int[]{imageInfo.getWidth() * 3});
//        List<FaceInfo> faceInfoList1 = new ArrayList<>();
//        errorCode = faceEngine.detectFaces(imageInfoEx, DetectModel.ASF_DETECT_MODEL_RGB, faceInfoList1);
//
//        FunctionConfiguration fun = new FunctionConfiguration();
//        fun.setSupportAge(true);
//        errorCode = faceEngine.process(imageInfoEx, faceInfoList1, functionConfiguration);
//        List<AgeInfo> ageInfoList1 = new ArrayList<>();
//        int age = faceEngine.getAge(ageInfoList1);
//        log.info("年龄：" + ageInfoList1.get(0).getAge());
//
//        FaceFeature feature = new FaceFeature();
//        errorCode = faceEngine.extractFaceFeature(imageInfoEx, faceInfoList1.get(0), feature);


        CzJsTrainImgs trainImgs = new CzJsTrainImgs();
        trainImgs.setId(imgId);
        trainImgs.setCheckType(checkType);
        float similarScore = faceSimilar.getScore();
        trainImgs.setSimilarScore(String.valueOf(similarScore));
        //引擎卸载
        errorCode = faceEngine.unInit();

        if(similarScore >= czPlatConfigTimeChecksService.getConfigValueById(36)){
            trainImgs.setCheckStatus(1);
            czJsTrainImgsService.updateCheckResult(trainImgs);
            return "{\"status\":0,\"data\":null,\"message\":\"比对成功\"}";
        }
        trainImgs.setCheckStatus(2);
        czJsTrainImgsService.updateCheckResult(trainImgs);
        return "{\"status\":1,\"data\":null,\"message\":\"比对失败\"}";
    }

    @RequestMapping(value = "api/face/similar/batch", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String batchSimulator(@RequestParam(value = "min") int min,@RequestParam(value = "max") int max){
//            ValueOperations<String,String> operations =  stringRedisTemplate.opsForValue();
//            String key = "min:id:img";
//            int value = Integer.valueOf(operations.get(key));
//            if(value > max) return "结束";
//            this.similar(value,2);
//            operations.set(key,String.valueOf(++value),24, TimeUnit.MINUTES);
            for(;min<=max;min ++){
//                Thread.sleep(500);
//                if(min%50 == 0) Thread.sleep(1000*60);
//                if(min%100 == 0) Thread.sleep(1000*120);
                this.similar(min,2);
//                log.info(min+" ");
//                czJsDevicesService.testThread();
            }
        return "ok";
    }

    @RequestMapping(value = "api/face/similar/test", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String testSimulator(@RequestParam(value = "min") int min,@RequestParam(value = "max") int max){
        for(;min<=max;min ++){
                log.info(min+" ");
                czJsDevicesService.testThread();
        }
        return "ok";
    }

    @RequestMapping(value = "api/face/similar/setValue", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String setValue(@RequestParam(value = "min") int min){
        ValueOperations<String,String> operations =  stringRedisTemplate.opsForValue();
        String key = "min:id:img";
        operations.set(key,String.valueOf(min),24, TimeUnit.MINUTES);
        return "ok";
    }

    /**
     * @Description 检测是否有人脸
     * @Param [path]
     * @return java.lang.String
     * @Author lynn 14:55 2023/7/31
    **/
    @RequestMapping(value = "api/face/isPerson", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String isPerson(@RequestParam(value = "path") String path){
        FaceEngine faceEngine = new FaceEngine(FaceConfig.libPath);
        //激活引擎
        int errorCode = faceEngine.activeOnline(FaceConfig.appId, FaceConfig.sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            log.error("引擎激活失败{}",errorCode);
            //引擎卸载
            errorCode = faceEngine.unInit();
            return "{\"status\":1,\"data\":null,\"message\":\"引擎激活失败\"}";
        }


        ActiveFileInfo activeFileInfo=new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            log.error("获取激活文件信息失败");
            //引擎卸载
            errorCode = faceEngine.unInit();
            return "{\"status\":1,\"data\":null,\"message\":\"获取激活文件信息失败\"}";
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);


        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            log.error("初始化引擎失败");
            //引擎卸载
            errorCode = faceEngine.unInit();
            return "{\"status\":1,\"data\":null,\"message\":\"初始化引擎失败\"}";
        }


        //人脸检测
        ImageInfo imageInfo = getRGBData(new File(FaceConfig.prePath+path));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        if(faceInfoList.size() != 1){
            errorCode = faceEngine.unInit();
            //引擎卸载
            return "{\"status\":1,\"data\":null,\"message\":\"未检测出人脸特征或人脸特征不唯一\"}";
        }
        //设置活体测试
        errorCode = faceEngine.setLivenessParam(0.5f, 0.7f);
        //人脸属性检测
        FunctionConfiguration configuration = new FunctionConfiguration();;
        configuration.setSupportLiveness(true);
        errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);




        //活体检测
        List<LivenessInfo> livenessInfoList = new ArrayList<LivenessInfo>();
        errorCode = faceEngine.getLiveness(livenessInfoList);
        //引擎卸载
        errorCode = faceEngine.unInit();
        if(errorCode == 0 && livenessInfoList.get(0).getLiveness() == 1){
            return "{\"status\":0,\"data\":null,\"message\":\"检测出活体人脸特征\"}";
        }
        return "{\"status\":1,\"data\":null,\"message\":\"未检测出活体特征\"}";
    }

    @RequestMapping(value = "api/login/check", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String loginCheck(@RequestParam(value = "path") String path,@RequestParam(value = "checkPath") String checkPath){
        FaceEngine faceEngine = new FaceEngine(FaceConfig.libPath);
        //激活引擎
        int errorCode = faceEngine.activeOnline(FaceConfig.appId, FaceConfig.sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            log.error("引擎激活失败{}",errorCode);
            //引擎卸载
            errorCode = faceEngine.unInit();
            return "{\"status\":1,\"data\":null,\"message\":\"引擎激活失败\"}";
        }


        ActiveFileInfo activeFileInfo=new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            log.error("获取激活文件信息失败");
            //引擎卸载
            errorCode = faceEngine.unInit();
            return "{\"status\":1,\"data\":null,\"message\":\"获取激活文件信息失败\"}";
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);


        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            log.error("初始化引擎失败");
            //引擎卸载
            errorCode = faceEngine.unInit();
            return "{\"status\":1,\"data\":null,\"message\":\"初始化引擎失败\"}";
        }


        //人脸检测
        ImageInfo imageInfo = getRGBData(new File(FaceConfig.prePath+path));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        if(faceInfoList.size() != 1){
            //引擎卸载
            errorCode = faceEngine.unInit();
            return "{\"status\":1,\"data\":null,\"message\":\"未检测出人脸特征\"}";
        }
        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
//        log.info("1特征值大小：" + faceFeature.getFeatureData().length);

        //人脸检测2
        ImageInfo imageInfo2 = getRGBData(new File(FaceConfig.prePath+checkPath));
        List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(),imageInfo2.getImageFormat(), faceInfoList2);
//        System.out.println(faceInfoList2);
        if(faceInfoList2.size() != 1){
            //引擎卸载
            errorCode = faceEngine.unInit();
            return "{\"status\":1,\"data\":null,\"message\":\"未检测出人脸特征\"}";
        }
        //特征提取2
        FaceFeature faceFeature2 = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo2.getImageFormat(), faceInfoList2.get(0), faceFeature2);
//        log.info("2特征值大小：" + faceFeature2.getFeatureData().length);

        //特征比对
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
        FaceSimilar faceSimilar = new FaceSimilar();

        errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);

        float similarScore = faceSimilar.getScore();
        //引擎卸载
        errorCode = faceEngine.unInit();

//        log.info("相似度：{}",similarScore);
        if(similarScore >= czPlatConfigTimeChecksService.getConfigValueById(36)){
            return "{\"status\":0,\"data\":"+similarScore+",\"message\":\"比对成功\"}";
        }

        return "{\"status\":1,\"data\":"+similarScore+",\"message\":\"比对失败\"}";
    }
}