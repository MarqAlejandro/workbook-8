package com.CarDealership.data;

import com.microsoft.sqlserver.jdbc.ISQLServerDataSource;

import java.sql.*;

public class DatabaseDriver {

            private static final String url = "jdbc:sqlserver://skills4it.database.windows.net:1433;" +
                    "database=Courses;" +
                    "user=user419@skills4it;" +
                    "password=YearupSecure2025!;" +
                    "encrypt=true;" +
                    "trustServerCertificate=false;" +
                    "loginTimeout=30;";


            public static String getUrl(){
                    return url;

            }




}