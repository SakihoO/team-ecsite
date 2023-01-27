package jp.co.internous.garnet.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.internous.garnet.model.domain.MstUser;

@Mapper
public interface MstUserMapper {
	
	//■mst_userテーブルに新規ユーザーのデータを登録する
	@Insert("INSERT INTO mst_user ("
			+ "user_name, password, "
			+ "family_name, first_name, family_name_kana, first_name_kana, "
			+ "gender"
			+ ") "
			+ "VALUES ("
			+ "#{userName}, #{password}, "
			+ "#{familyName}, #{firstName}, #{familyNameKana}, #{firstNameKana}, "
			+ "#{gender}"
			+ ")")
	@Options(useGeneratedKeys=true, keyProperty="id")
	int insert(MstUser mstUser);
	
	//■ユーザー名とパスワードを条件に、ユーザー情報を取得する
	@Select("SELECT * FROM mst_user WHERE user_name = #{userName} AND password = #{password}")
	MstUser findByUserNameAndPassword(
			@Param("userName") String userName,
			@Param("password") String password);
	
	//■ユーザー名を条件に、件数を取得する
	@Select("SELECT count(id) FROM mst_user WHERE user_name = #{userName}")
	int findCountByUserName(String userName);
	
	//■ユーザー名を条件に、DBのパスワードを更新する
	@Update("UPDATE mst_user SET password = #{password}, updated_at = now() WHERE user_name = #{userName}")
	int updatePassword(
			@Param("userName") String userName,
			@Param("password") String password);

}