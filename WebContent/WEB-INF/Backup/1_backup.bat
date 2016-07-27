IF NOT EXIST "C:\TTFAuditorBackup" (
	md "C:\TTFAuditorBackup"	
)
cd\
cd "C:\Program Files\MySQL\MySQL Server 5.7\bin"
mysqldump --add-drop-database --add-drop-table -h 10.14.124.14 --port 3306 -u integra --password=root -B auditoria_db -r "C:\TTFAuditorBackup\backup.sql"

net stop MySQL57
net start MySQL57