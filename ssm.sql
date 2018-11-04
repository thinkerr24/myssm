/*
 *  Create by rr on 2018/10/29
 */

CREATE DATABASE IF NOT EXISTS ssm DEFAULT CHARACTER SET utf8;
USE ssm;

DROP TABLE IF EXISTS tb_dept;
CREATE TABLE `tb_dept` (
  `dept_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '部门号',
  `dept_name` VARCHAR(255) NOT NULL COMMENT '部门名',
  PRIMARY KEY (`dept_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS tb_emp;
CREATE TABLE `tb_emp` (
  `emp_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '员工号',
  `emp_name` VARCHAR(255) NOT NULL COMMENT '员工姓名',
  `gender` CHAR(1) DEFAULT NULL COMMENT '员工性别',
  `email` VARCHAR(255) DEFAULT NULL COMMENT '员工邮件',
  `d_id` INT(11) DEFAULT NULL COMMENT '外键, 员工所属的部门号',
  PRIMARY KEY (`emp_id`),
  KEY `FK_tb_emp` (`d_id`),
  CONSTRAINT `FK_tb_emp` FOREIGN KEY (`d_id`) REFERENCES `tb_dept` (`dept_id`)
) ENGINE=INNODB  CHARSET=utf8;