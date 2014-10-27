package edu.utdallas.videoOnDemand.dao.impl;

import javax.sql.DataSource;


public class BaseDAO
{
    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
