package com.xinyijia.backend.mapper;

import com.xinyijia.backend.domain.CompanyInfo;
import com.xinyijia.backend.domain.CompanyInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CompanyInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    int countByExample(CompanyInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    int deleteByExample(CompanyInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    int insert(CompanyInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    int insertSelective(CompanyInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    List<CompanyInfo> selectByExampleWithBLOBs(CompanyInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    List<CompanyInfo> selectByExample(CompanyInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    CompanyInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    int updateByExampleSelective(@Param("record") CompanyInfo record, @Param("example") CompanyInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    int updateByExampleWithBLOBs(@Param("record") CompanyInfo record, @Param("example") CompanyInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    int updateByExample(@Param("record") CompanyInfo record, @Param("example") CompanyInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    int updateByPrimaryKeySelective(CompanyInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(CompanyInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table company_info
     *
     * @mbggenerated Sun May 20 00:15:43 CST 2018
     */
    int updateByPrimaryKey(CompanyInfo record);
}