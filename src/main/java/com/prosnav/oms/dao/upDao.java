package com.prosnav.oms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.prosnav.oms.util.jdbcUtil;

public class upDao {
	private static  JdbcTemplate jt = (JdbcTemplate) jdbcUtil
            .getBean("template");
	/**
	 * 添加kyc
	 * @param datas
	 */
	public static void kycinsert(String[] datas,long cust_id){
		long id = jdbcUtil.seq();
		Object[] o = datas;
		String kyc_no = datas[0]; 
		String sql = "INSERT INTO public.kyc(kyc_id, kyc_no, reg_time, chinese_name, english_name, gender, "
				+ "birthday, education, education_date, idtype, id_no, id_effect_date, "
				+ "marital_status, nationality, citizenship, email, home_phone, "
				+ "wechat, mobie_phone, fax_no, office_no, relation_name, relation_tel, "
				+ "relation_phone, relation, home_address, home_postal_code, company_address, "
				+ "company_postal_code, correspondence_address, corresponde_postal_code, "
				+ "collection_method, collection_email, preferrd_language, yuan_bank_name, "
				+ "yuan_bank_address, yuan_bank_no, dollar_bank_name, dollar_bank_address, "
				+ "dollar_bank_no, guardian_name, guardian_phone, guardian_idtype, guardian_idno, "
				+ "guardian_effect_date, employer_name, unit_nature, job_year, career, position, working_field,"
						+ " house, house_area, house_money, car_brand, car_money, other_org, finance_org_name, "
						+ "prod_type, wealth_from, fund_from, annual_income, net_flow_value, "
						+ "trading_experience, trading_experience_year, trading_aim, final_account_owner, "
						+ "final_account_owner_info, final_owner, final_owner_info, is_license, "
						+ "is_prosnav_rel, is_spouse_prosnav, is_hoiding, cust_signature, cust_signature_date)  "
						+ "VALUES ("+id+", ?, current_date, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
						+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		addCourse(sql, o);
		String kyc_relsql = "INSERT INTO public.kyc_cust_rel("
				+ "cust_id, kyc_id, email_id, kyc_no)"
				+ "VALUES (?, ?, ?, ?)";
		jt.update(kyc_relsql,cust_id,id,jdbcUtil.seq(),kyc_no);

	}
	
	public static long addCourse(String sql, Object[] o) {
		final String innersql = sql;
		final Object[] innerO = o;
		KeyHolder keyHolder = new GeneratedKeyHolder();

		// int projectid = jt.update(sql);

		
			jt.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = jt.getDataSource().getConnection().prepareStatement(innersql,
							Statement.RETURN_GENERATED_KEYS);
					for (int i = 0; i < innerO.length; i++) {
						ps.setObject(i + 1, innerO[i]);
					}

					return ps;
				}
			}, keyHolder);
		
		//Map<String, Object> generatedId = keyHolder.getKeyList().get(0);
		//long o_id = (Long) generatedId.get(id);
		// System.out.println("自动插入id============================" +
		// keyHolder.getKey().intValue());
		return 0;

	}
}
