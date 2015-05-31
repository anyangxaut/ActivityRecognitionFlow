# --------------------------------------------------------
# Host:                         127.0.0.1
# Server version:               5.1.44-community
# Server OS:                    Win32
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2015-05-28 16:58:36
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

# Dumping database structure for activityrecognition
CREATE DATABASE IF NOT EXISTS `activityrecognition` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `activityrecognition`;


# Dumping structure for table activityrecognition.featureextraction
CREATE TABLE IF NOT EXISTS `featureextraction` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `SensorId` int(10) NOT NULL COMMENT '传感器ID',
  `AccX_mean` text NOT NULL COMMENT '单位：mg',
  `AccY_mean` text NOT NULL COMMENT '单位：mg',
  `AccZ_mean` text NOT NULL COMMENT '单位：mg',
  `AccX_variance` text NOT NULL COMMENT '单位：mg',
  `AccY_variance` text NOT NULL COMMENT '单位：mg',
  `AccZ_variance` text NOT NULL COMMENT '单位：mg',
  `AccX_AccY_correlation` text NOT NULL COMMENT '单位：mg',
  `AccY_AccZ_correlation` text NOT NULL COMMENT '单位：mg',
  `AccX_AccZ_correlation` text NOT NULL COMMENT '单位：mg',
  `AccX_energy` text NOT NULL COMMENT '单位：mg',
  `AccY_energy` text NOT NULL COMMENT '单位：mg',
  `AccZ_energy` text NOT NULL COMMENT '单位：mg',
  `locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；3：Sit；4：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='特征提取后的数据信息（均值，方差，相关系数，能量）';

# Data exporting was unselected.


# Dumping structure for table activityrecognition.lie
CREATE TABLE IF NOT EXISTS `lie` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `SensorId` int(10) NOT NULL COMMENT '传感器ID',
  `AccX` text NOT NULL COMMENT '单位：mg',
  `AccY` text NOT NULL COMMENT '单位：mg',
  `AccZ` text NOT NULL COMMENT '单位：mg',
  `Locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；4：Sit；5：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Locomotion=5(lie)';

# Data exporting was unselected.


# Dumping structure for table activityrecognition.preprocessingdata
CREATE TABLE IF NOT EXISTS `preprocessingdata` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `Time` text NOT NULL COMMENT '单位：ms',
  `RKN_accX` text NOT NULL COMMENT '单位：mg',
  `RKN_accY` text NOT NULL COMMENT '单位：mg',
  `RKN_accZ` text NOT NULL COMMENT '单位：mg',
  `HIP_accX` text NOT NULL COMMENT '单位：mg',
  `HIP_accY` text NOT NULL COMMENT '单位：mg',
  `HIP_accZ` text NOT NULL COMMENT '单位：mg',
  `LUA_accX` text NOT NULL COMMENT '单位：mg',
  `LUA_accY` text NOT NULL COMMENT '单位：mg',
  `LUA_accZ` text NOT NULL COMMENT '单位：mg',
  `Locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；4：Sit；5：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='经过预处理后的数据（预处理：删除含有缺失数据或是Locomotion标签为0的数据item）';

# Data exporting was unselected.


# Dumping structure for table activityrecognition.sit
CREATE TABLE IF NOT EXISTS `sit` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `SensorId` int(10) NOT NULL COMMENT '传感器ID',
  `AccX` text NOT NULL COMMENT '单位：mg',
  `AccY` text NOT NULL COMMENT '单位：mg',
  `AccZ` text NOT NULL COMMENT '单位：mg',
  `Locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；4：Sit；5：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Locomotion=4(Sit)';

# Data exporting was unselected.


# Dumping structure for table activityrecognition.stand
CREATE TABLE IF NOT EXISTS `stand` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `SensorId` int(10) NOT NULL COMMENT '传感器ID',
  `AccX` text NOT NULL COMMENT '单位：mg',
  `AccY` text NOT NULL COMMENT '单位：mg',
  `AccZ` text NOT NULL COMMENT '单位：mg',
  `Locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；4：Sit；5：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Locomotion=1(Stand)';

# Data exporting was unselected.


# Dumping structure for table activityrecognition.walk
CREATE TABLE IF NOT EXISTS `walk` (
  `Id` int(10) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `SensorId` int(10) NOT NULL COMMENT '传感器ID',
  `AccX` text NOT NULL COMMENT '单位：mg',
  `AccY` text NOT NULL COMMENT '单位：mg',
  `AccZ` text NOT NULL COMMENT '单位：mg',
  `Locomotion` text NOT NULL COMMENT '1：Stand；2：Walk；4：Sit；5：Lie',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Locomotion=2(Walk)';

# Data exporting was unselected.
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
