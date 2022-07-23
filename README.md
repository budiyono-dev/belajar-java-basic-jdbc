# belajar-java-basic-jdbc
Belajar basic jdbc di java dengan Mysql

## Table user for test
Buat database sebelum menjalankan tes, pastikan setiap test database dibersihkan.
dan dibuat ulang.
```sql
CREATE TABLE IF NOT EXISTS `user`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `firsname` varchar(100) NOT NULL,
  `lastname` varchar(100) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_unique` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

```

drop table sql
```sql
DROP TABLE IF EXISTS USER;
```
delete all data
```sql
DELETE FROM USER;
```
