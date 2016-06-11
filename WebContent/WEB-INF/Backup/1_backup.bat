cd\
cd "C:\Program Files\MySQL\MySQL Server 5.6\bin"
mysqldump --add-drop-database --add-drop-table -h 192.168.25.5 --port 3306 -u root --password=root -B auditoria_db -r "C:\TTFAuditorBackup\backup.sql"

net stop MySQL56
net start MySQL56