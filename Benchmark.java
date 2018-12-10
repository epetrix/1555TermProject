import java.sql.*;

public class Benchmark
{
	public static void main(String[] args)
	{
		Connection connection = MyAuction.serverLogin();
		Administrator administrator = new Administrator(connection, "admin");

		//benchmarking registerCustomer() by calling addUser 20 times
		administrator.addUser("abc", "abc", "abc", "123 abc road, Pittsburgh, PA 15213", "abc@pitt.edu", true);
		administrator.addUser("def", "def", "def", "456 def road, Pittsburgh, PA 15213", "def@pitt.edu", true);
		administrator.addUser("ghi", "ghi", "ghi", "789 ghi road, Pittsburgh, PA 15213", "ghi@pitt.edu", true);
		administrator.addUser("jkl", "jkl", "jkl", "123 jkl road, Pittsburgh, PA 15213", "jkl@pitt.edu", true);
		administrator.addUser("mno", "mno", "mno", "123 mno road, Pittsburgh, PA 15213", "mno@pitt.edu", true);
		administrator.addUser("pqr", "pqr", "pqr", "456 pqr road, Pittsburgh, PA 15213", "pqr@pitt.edu", true);
		administrator.addUser("stu", "stu", "stu", "789 stu road, Pittsburgh, PA 15213", "stu@pitt.edu", true);
		administrator.addUser("vwx", "vwx", "vwx", "123 vwx road, Pittsburgh, PA 15213", "vwx@pitt.edu", true);
		administrator.addUser("yz", "yz", "yz", "456 yz road, Pittsburgh, PA 15213", "yz@pitt.edu", true);
		administrator.addUser("zyx", "zyx", "zyx", "789 zyx road, Pittsburgh, PA 15213", "zyx@pitt.edu", true);
		administrator.addUser("wvu", "wvu", "wvu", "123 wvu road, Pittsburgh, PA 15213", "wvu@pitt.edu", true);
		administrator.addUser("tsr", "tsr", "str", "456 tsr road, Pittsburgh, PA 15213", "tsr@pitt.edu", true);
		administrator.addUser("qpo", "qpo", "qpo", "789 qpo road, Pittsburgh, PA 15213", "qpo@pitt.edu", true);
		administrator.addUser("nml", "nml", "nml", "123 nml road, Pittsburgh, PA 15213", "nml@pitt.edu", true);
		administrator.addUser("kji", "kji", "kji", "456 kji road, Pittsburgh, PA 15213", "kji@pitt.edu", true);
		administrator.addUser("hgf", "hgf", "hgf", "789 hgf road, Pittsburgh, PA 15213", "hgf@pitt.edu", true);
		administrator.addUser("edc", "edc", "edc", "123 edc road, Pittsburgh, PA 15213", "edc@pitt.edu", true);
		administrator.addUser("ba", "ba", "ba", "456 ba road, Pittsburgh, PA 15213", "ba@pitt.edu", true);
		administrator.addUser("gaf", "gaf", "gaf", "123 gaf road, Pittsburgh, PA 15213", "gaf@pitt.edu", true);
		administrator.addUser("sye", "sye", "sye", "123 sye road, Pittsburgh, PA 15213", "sye@pitt.edu", true);


		//benchmarking updateSysDate()
		administrator.setDate("2019-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2020-02-02 01:01:01", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2021-03-03 02:02:02", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2022-04-04 03:03:03", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2023-05-05 04:04:04", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2024-06-06 05:05:05", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2025-07-07 06:06:06", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2026-08-08 07:07:07", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2027-09-09 08:08:08", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2028-10-10 09:09:09", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2029-11-11 10:10:10", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2030-12-12 11:11:11", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2031-01-01 12:12:12", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2032-02-02 13:13:13", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2033-03-03 14:14:14", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2034-04-04 15:15:15", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2035-05-05 16:16:16", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2036-06-06 17:17:17", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2037-07-07 18:18:18", "yyyy-MM-dd HH:mm:ss");
		administrator.setDate("2038-08-08 19:19:19", "yyyy-MM-dd HH:mm:ss");
		
		//benchmarking getProductStats

	}
}