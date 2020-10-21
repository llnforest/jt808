-- 添加表分区
alter table location add partition (
	partition P_2020_01_03 values less than ('2020-01-03'),
	partition P_2020_01_04 values less than ('2020-01-04')
);

-- 删除表分区
alter table location drop partition P_2020_01_04;

-- 查看分区
select *
from information_schema.partitions
where table_schema = schema() and table_name = 'location'
order by partition_ordinal_position desc;