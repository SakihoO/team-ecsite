package jp.co.internous.garnet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.garnet.model.domain.MstUser;
import jp.co.internous.garnet.model.form.UserForm;
import jp.co.internous.garnet.model.mapper.MstUserMapper;
import jp.co.internous.garnet.model.session.LoginSession;

@Controller
@RequestMapping("/garnet/user")
public class UserController {
	
	@Autowired
	private MstUserMapper userMapper;
	// MstUserMapperからmst_userテーブルにアクセスするDAO
	
	@Autowired
	private LoginSession loginSession;

	//■【新規ユーザー登録画面を初期表示する】
	@RequestMapping("/")
	public String index(Model m) {
		
		// page_header.htmlでsessionの変数を表示させているため、loginSessionを画面に送る。
		m.addAttribute("loginSession", loginSession);
		
		//戻り値で register_user.html を表示する
		return "register_user";
	}
	
	/*
	 * ■【ユーザー名の重複確認をする】
	 * 　DBテーブル(mst_user)から、ユーザー名で検索し、結果を取得
	 * 　戻り値　true：登録成功　false：登録失敗
	 */
	@RequestMapping("/duplicatedUserName")
	@ResponseBody
	public boolean duplicatedUserName(@RequestBody UserForm f) {
		
		// userMapperにいるfindCountByUserNameの件数を呼び出す
		int count = userMapper.findCountByUserName(f.getUserName());
		
		return count > 0;
	}
	
	//■【新規ユーザー登録内容確認ダイアログの登録ボタン押下】
	@RequestMapping("/register")
	@ResponseBody
	public boolean register(@RequestBody UserForm f) {
		MstUser user = new MstUser(f);
		
		//処理内容　MstUserMapperのinsert用メソッドの呼び出し
		int count = userMapper.insert(user);
		
		return count > 0;
	}
	
}
