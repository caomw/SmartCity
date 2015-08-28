package com.dc.smartcity.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.dcone.ut.view.annotation.ViewInject;
import com.android.dcone.ut.view.annotation.event.OnClick;
import com.dc.smartcity.R;
import com.dc.smartcity.base.BaseActionBarActivity;
import com.dc.smartcity.bean.user.UserAuthBean;
import com.dc.smartcity.bean.user.UserBaseBean;
import com.dc.smartcity.bean.user.UserLocalBean;
import com.dc.smartcity.bean.user.UserObj;
import com.dc.smartcity.dialog.DialogConfig;
import com.dc.smartcity.dialog.PicpickDlg;
import com.dc.smartcity.dialog.PicpickDlg.IPictureListener;
import com.dc.smartcity.litenet.RequestPool;
import com.dc.smartcity.litenet.interf.RequestProxy;
import com.dc.smartcity.util.MyCount;
import com.dc.smartcity.util.Utils;
import com.dc.smartcity.view.LoadingDialog;

/**
 * 实名认证接口 szsm_dyj 2015/08/20 create
 *
 */
public class AuthAct extends BaseActionBarActivity implements IPictureListener {

	// 登陆名
	@ViewInject(R.id.tv_loginname)
	TextView tv_loginname;
	// 获取验证码
	@ViewInject(R.id.tvGetVerify)
	TextView tvGetVerify;
	// 身份证姓名
	@ViewInject(R.id.et_idcard_name)
	EditText et_idcard_name;
	// 身份证号
	@ViewInject(R.id.et_idcard_no)
	EditText et_idcard_no;
	// 身份证正面照
	@ViewInject(R.id.iv_idcard_pic1)
	ImageView iv_idcard_pic1;
	// 身份证反面照
	@ViewInject(R.id.iv_idcard_pic2)
	ImageView iv_idcard_pic2;
	// 是否同意
	@ViewInject(R.id.cb_agree)
	CheckBox cb_agree;
	// 实名协议
	@ViewInject(R.id.tv_auth_rules)
	TextView tv_auth_rules;

	PicpickDlg picDlg;
	int type;
	// 正面
	static final int FPICTURE = 1;
	// 反面
	static final int BPICTURE = 2;

	// 相机
	static final int PICK_CAMERA = 100;
	// 相册
	static final int PICK_ALBUM = 200;

	private LoadingDialog load;

	@Override
	protected void setContentView() {
		setContentView(R.layout.act_auth);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initActionBar();
		initUserInfo();

	}

	private void initUserInfo() {
		tv_loginname.setText(Utils.user.userBase.login);
//		if (!TextUtils.isEmpty(Utils.user.userBase.name)) {
//			et_idcard_name.setText(Utils.user.userBase.name);
//		}
		load = LoadingDialog.create(this);
	}

	private void showProcess() {
		if (null != load && !load.isShowing()) {
			load.show();
		}
	}

	private void dismsProcess() {
		if (null != load && load.isShowing()) {
			load.dismiss();
		}
	}

	private void initActionBar() {
		iv_actionbar_left.setVisibility(View.VISIBLE);
		setActionBarTitle(getString(R.string.tv_real_auth));
	}

	@OnClick(value = { R.id.btn_auth, R.id.tvGetVerify, R.id.iv_idcard_pic1,
			R.id.iv_idcard_pic2,R.id.tv_auth_rules })
	private void onClcik(View v) {
		switch (v.getId()) {
		case R.id.btn_auth:
			doAuth();
			break;
		case R.id.tvGetVerify:
			sendVerifyCode();
			break;
		case R.id.iv_idcard_pic1:
			type = FPICTURE;
			showPickDialog();
			break;
		case R.id.tv_auth_rules:
			startActivity(new Intent(this, RegistActProtocal.class));
			break;
		case R.id.iv_idcard_pic2:
			type = BPICTURE;
			showPickDialog();
			break;
		default:
			break;
		}
	}

	private void showPickDialog() {
		if (null == picDlg) {
			picDlg = new PicpickDlg(this, this);
		}

		if (!picDlg.isShowing()) {
			picDlg.show();
		}
	}

	/**
	 * 实名认证
	 */
	private void doAuth() {
		String name = et_idcard_name.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			Utils.showToast("请填写真实姓名", this);
			return;
		}
		String idcardCode = et_idcard_no.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			Utils.showToast("请填写真实身份证号", this);
			return;
		}
//		if (null == fpic) {
//			Utils.showToast("请选择身份证正面照片", this);
//			return;
//		}
//		String fP = Utils.BitmapToBase64(fpic);
//		if (null == bpic) {
//			Utils.showToast("请选择身份证反面照片", this);
//			return;
//		}
//		String bP = Utils.BitmapToBase64(bpic);
		
		//Temp:
		String fP = "fp_tmp";
		String bP = "bp_tmp";
		if (!cb_agree.isChecked()) {
			Utils.showToast("请先阅读认证协议", this);
			return;
		}

		showProcess();
		sendRequestWithNoDialog(RequestPool.requestAuth(name, idcardCode, fP,
				bP, Utils.user.userAuth.mobilenum), new RequestProxy() {

			@Override
			public void onSuccess(String msg, String result) {
				Utils.showToast("实名认证申请提交成功", AuthAct.this);
				queryUserInfo();
			}
			@Override
			public void onError(String code, String msg) {
				Utils.showToast("实名认证申请失败:"+msg, AuthAct.this);
				dismsProcess();
			}

		});
	}

	/**
	 * 
	 */
	private void queryUserInfo() {
		sendRequestWithNoDialog(RequestPool.requestUserInfo(),
				new RequestProxy() {

					@Override
					public void onSuccess(String msg, String result) {
						dismsProcess();
						JSONObject obj = JSON.parseObject(result);
						UserObj user = new UserObj();
						user.userBase = JSON.parseObject(
								obj.getString("USERBASIC"), UserBaseBean.class);
						user.userAuth = JSON.parseObject(
								obj.getString("USERAUTH"), UserAuthBean.class);
						user.userLocal = JSON.parseObject(
								obj.getString("LOCALUSER"), UserLocalBean.class);

						if (null != user.userBase) {
							Utils.user = user;
						}
						// finish();
					}

					@Override
					public void onCancel(String msg) {
						dismsProcess();
					}
				});
	}

	MyCount mc;

	/**
	 * 发送短信验证码
	 */
	private void sendVerifyCode() {
		if (null == mc) {
			mc = new MyCount(this, tvGetVerify);
		}
		mc.start();
		sendRequestWithDialog(
				RequestPool.getVerifyCode(Utils.user.userAuth.mobilenum, "06"),
				new DialogConfig.Builder().build(), new RequestProxy() {

					@Override
					public void onSuccess(String msg, String result) {
						Utils.showToast("发送成功", AuthAct.this);
					}
				});

	}

	Bitmap fpic;
	Bitmap bpic;

	@Override
	public void notifyCamera() {
		if (type == FPICTURE) {
			fpic = null;
		} else {
			bpic = null;
		}

		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			startActivityForResult(intent, PICK_CAMERA);
		} else {
			Utils.showToast("请插入SD卡", this);
		}

	}

	@Override
	public void notifyAlbum() {
		if (type == FPICTURE) {
			fpic = null;
		} else {
			bpic = null;
		}
		/***
		 * 这个是调用android内置的intent，来过滤图片文件 ，同时也可以过滤其他的
		 */
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, PICK_ALBUM);
	}

	Bitmap bitmap;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("AuthAct", "null == data" + (null == data));
		if (resultCode == RESULT_OK && null != data) {// 相册选取图片

			switch (requestCode) {
			case PICK_ALBUM:
				/**
				 * 当选择的图片不为空的话，在获取到图片的途径
				 */
				Log.e("AuthAct", "PICK_ALBUM");
				// Uri uri = data.getData();
				try {
					Uri selectedImage = data.getData();
					String path = getPickPhotoPath(this, data);
					/***
					 * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话， 你选择的文件就不一定是图片了，
					 * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
					 */
					String end = path.toLowerCase();
					if (end.endsWith("jpg") || end.endsWith("png")
							|| end.endsWith("jpeg")) {
						// picPath = path;
						bitmap = BitmapFactory
								.decodeStream(getContentResolver()
										.openInputStream(selectedImage));
						bitmap = scaleBitmap(bitmap);
						Log.e("AuthAct", "null != bitmap");
						saveImageFile();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case PICK_CAMERA:
				try {
					if (data.getExtras() != null) {
						Bundle extras = data.getExtras();
						Uri uriss = (Uri) extras.get(MediaStore.EXTRA_OUTPUT);

						bitmap = (Bitmap) extras.get("data");
						bitmap = scaleBitmap(bitmap);

					} else {
						Uri uris = data.getData();
						String filePath = null;
						if (null != uris)// HTC and normal
						{
							// 根据返回的URI获取对应的SQLite信息
							Cursor cursor = this.getContentResolver().query(
									uris, null, null, null, null);
							if (cursor.moveToFirst()) {
								filePath = cursor
										.getString(cursor
												.getColumnIndex(MediaStore.Images.Media.DATA));// 获取绝对路径
							}
							cursor.close();

							if (!TextUtils.isEmpty(filePath)) {

								/**
								 * 该Bitmap是为了获取压缩后的文件比例 如果没有缩略图的比例
								 * 就获取真实文件的比例(真实图片比例会耗时很长，所以如果有缩略图最好)
								 */
								bitmap = BitmapFactory.decodeFile(filePath);
								bitmap = scaleBitmap(bitmap);
							}
						}
					}
					if (null != bitmap) {
						saveImageFile();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				break;

			default:
				break;
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 获取选择图片路径
	 * 
	 * @param activity
	 * @param data
	 * @return path
	 */
	public static String getPickPhotoPath(Activity activity, Intent data) {
		String path = "";
		Uri imageuri = data.getData();
		if (null != imageuri && imageuri.getScheme().compareTo("file") == 0) {
			path = imageuri.toString().replace("file://", "");
		} else {
			if (imageuri != null) {
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor cursor = activity.managedQuery(imageuri, proj, null,
						null, null);
				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				if (cursor.moveToFirst()) {
					path = cursor.getString(column_index);
				}
			}
		}
		return path;
	}

	private void saveImageFile() {
		if (type == FPICTURE) {
			fpic = bitmap;
			iv_idcard_pic1.setImageBitmap(fpic);
		} else {
			bpic = bitmap;
			iv_idcard_pic2.setImageBitmap(bpic);
		}
	}

	private Bitmap scaleBitmap(Bitmap bitmap) {
		if (bitmap != null) {
			float width = bitmap.getWidth();
			float height = bitmap.getHeight();
			if (width > 0 && height > 0) {

				float dstWidth = 600;
				float dstHeight = 400;

				if (width > height) {
					dstHeight = (height * dstWidth) / width;
				} else {
					dstWidth = (width * dstHeight) / height;
				}
				bitmap = Bitmap.createScaledBitmap(bitmap, (int) dstWidth,
						(int) dstHeight, true);// Bitmap.createBitmap(view.getDrawingCache());
			}

			return bitmap;
		}
		return null;
	}
}
