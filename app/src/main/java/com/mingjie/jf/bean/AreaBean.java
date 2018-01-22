package com.mingjie.jf.bean;

import java.util.List;

public class AreaBean
{

    public List<DataEntity> data;

    public class DataEntity
    {

        public int provinceId;
        public String provinceType;
        public List<CityBean> cityList;

        public class CityBean
        {
            public int cityId;
            public String cityType;

            @Override
            public String toString()
            {
                return "CityBean{" +
                        "cityId=" + cityId +
                        ", cityType='" + cityType + '\'' +
                        '}';
            }
        }
    }
}
