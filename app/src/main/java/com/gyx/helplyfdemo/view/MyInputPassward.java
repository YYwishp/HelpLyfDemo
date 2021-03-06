package com.gyx.helplyfdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import com.gyx.helplyfdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 密码输入
 * Created by gyx on 2017/11/4.
 */
public class MyInputPassward extends android.support.v7.widget.AppCompatEditText {
	private Paint rectPaint;
	private Paint textPaint;
	private Rect textRect;
	private boolean isFocus = false;
	private String text = "";
	private List<Rect> list = new ArrayList<>();
	/**
	 * 密码显示方式
	 */
	private PwdType pwdType;
	/**
	 * 是否密文显示
	 */
	private boolean isPwd;
	/**
	 * 是否需要在输入完成后关闭键盘
	 */
	private boolean isAutoCloseKeyBoard = true;
	/**
	 * 是否有圆角
	 */
	private boolean isCorner;
	/**
	 * 横向间距
	 */
	private int widthSpace;
	/**
	 * 圆形的样子
	 */
	private int pwdType_CircleRadius;
	/**
	 * 纵向间距
	 */
	private int heightSpace;
	/**
	 * 密码框的宽度
	 */
	private int rectStroke;
	/**
	 * 字体大小
	 */
	private int txtSize;
	/**
	 * 边框或者实体框
	 */
	private boolean isBgFill;
	/**
	 * 密码长度
	 */
	private int numLength;
	/**
	 * 字体颜色
	 */
	private int textColor;
	/**
	 * 圆角边框颜色
	 */
	private int broderColor;
	/**
	 * 虚线颜色
	 */
	private int dottedColor;
	/**
	 * 默认框框颜色
	 */
	private int rectNormalColor;
	/**
	 * 选中框框颜色
	 */
	private int rectChooseColor;
	/**
	 * 是否是阿拉伯国家
	 */
	private boolean isArCountry = false;
	/**
	 * 圆角角度
	 */
	private float radian = 15;
	private Paint borderPaint;
	private Paint dottedPaint;
	private int broderWidt;
	private boolean isNeedDash;

	///////////////////////////////////////////////////////////////////////////
	// 以上是属性
	///////////////////////////////////////////////////////////////////////////
	public void setPwdType(PwdType pwdType) {
		this.pwdType = pwdType;
	}

	public boolean isFocus() {
		return isFocus;
	}

	public void setFocus(boolean focus) {
		isFocus = focus;
	}

	public boolean isPwd() {
		return isPwd;
	}

	public void setIsPwd(boolean pwd) {
		isPwd = pwd;
	}



	public void setWidthSpace(int widthSpace) {
		this.widthSpace = widthSpace;
	}

	public void setHeightSpace(int heightSpace) {
		this.heightSpace = heightSpace;
	}

	public void setRectStroke(int rectStroke) {
		this.rectStroke = rectStroke;
	}

	public void setTxtSize(int txtSize) {
		this.txtSize = txtSize;
	}

	public boolean isBgFill() {
		return isBgFill;
	}

	public void setIsBgFill(boolean bgFill) {
		this.isBgFill = bgFill;
	}

	public void setNumLength(int numLength) {
		this.numLength = numLength;
	}

	public void setRectChooseColor(int rectChooseColor) {
		this.rectChooseColor = rectChooseColor;
	}

	public void setRectNormalColor(int rectNormalColor) {
		this.rectNormalColor = rectNormalColor;
	}

	public void setArCountry(boolean arCountry) {
		isArCountry = arCountry;
	}

	@Override
	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public enum PwdType {
		CIRCLE,
		XINGHAO
	}


	public MyInputPassward(Context context) {
		super(context);
		setAttr(null, 0);
		init();
	}

	public MyInputPassward(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttr(attrs, 0);
		init();
	}

	public MyInputPassward(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setAttr(attrs, defStyleAttr);
		init();
	}

	private onInputOverListener onInputOverListener;

	public interface onInputOverListener {
		void onInputOver(String text);
	}

	public void setOnInputOverListener(onInputOverListener onInputOverListener) {
		this.onInputOverListener = onInputOverListener;
	}

	private void setAttr(AttributeSet attrs, int defStyleAttr) {
		TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.MyPasswordInputEdt, defStyleAttr, 0);
		isPwd = a.getBoolean(R.styleable.MyPasswordInputEdt_isPwd, true);
		isAutoCloseKeyBoard = a.getBoolean(R.styleable.MyPasswordInputEdt_autoCloseKeyBoard, true);

		widthSpace = a.getDimensionPixelSize(R.styleable.MyPasswordInputEdt_widthSpace, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
		pwdType_CircleRadius = a.getDimensionPixelSize(R.styleable.MyPasswordInputEdt_circleRadius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
		heightSpace = a.getDimensionPixelSize(R.styleable.MyPasswordInputEdt_heightSpace, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
		rectStroke = a.getDimensionPixelSize(R.styleable.MyPasswordInputEdt_rectStroke, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
		txtSize = a.getDimensionPixelSize(R.styleable.MyPasswordInputEdt_txtSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));
		isBgFill = a.getBoolean(R.styleable.MyPasswordInputEdt_bgFill, false);
		numLength = a.getInt(R.styleable.MyPasswordInputEdt_numLength, 6);
		textColor = a.getColor(R.styleable.MyPasswordInputEdt_textColor, 0xff666666);

		isCorner = a.getBoolean(R.styleable.MyPasswordInputEdt_isCorner, true);
		isNeedDash = a.getBoolean(R.styleable.MyPasswordInputEdt_isNeedDash, true);
		//圆角边框颜色
		broderColor = a.getColor(R.styleable.MyPasswordInputEdt_broderColor, 0xff666666);
		//圆角边框厚度
		broderWidt = a.getDimensionPixelSize(R.styleable.MyPasswordInputEdt_broderWidth, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
		//虚线颜色
		dottedColor = a.getColor(R.styleable.MyPasswordInputEdt_dottedColor, 0xff666666);
		rectNormalColor = a.getColor(R.styleable.MyPasswordInputEdt_rectNormalColor, 0xff808080);
		rectChooseColor = a.getColor(R.styleable.MyPasswordInputEdt_rectChooseColor, 0xff44ce61);
		pwdType = a.getInt(R.styleable.MyPasswordInputEdt_pwdType, 0) == 0 ? PwdType.CIRCLE : PwdType.XINGHAO;
		a.recycle();
	}

	@Override
	protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
		if (this.text == null) {
			return;
		}
		if (this.text.length() < numLength) {
			this.text = this.text + text.toString();
			if (onInputOverListener != null) {
				onInputOverListener.onInputOver(this.text);
			}
		} else {
			if (onInputOverListener != null) {
				onInputOverListener.onInputOver(this.text);
				if (isAutoCloseKeyBoard) {
					closeKeybord();
				}
			}
		}
		if (text.toString().length() != 0) {
			setText("");
		}
	}


	public String getTextToStirng() {
//		Editable text = super.getText();
		return this.text;
	}
	/**
	 * 这个方法用在使用了自定义键盘的情况下，其他条件下不能使用
	 * 涉及到重新绘制
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
		invalidate();

	}

	/**
	 * 关闭软键盘
	 */
	public void closeKeybord() {
		InputMethodManager imm = (InputMethodManager) getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getWindowToken(), 0);
	}

	private void init() {
		//矩形体
		rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		//文字
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		//圆角边框
		borderPaint = new Paint((Paint.ANTI_ALIAS_FLAG));
		//虚线
		dottedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		//
		borderPaint.setStyle(Paint.Style.STROKE);
		dottedPaint.setStyle(Paint.Style.STROKE);
		textPaint.setStyle(Paint.Style.FILL);
		dottedPaint.setStrokeWidth(3);
		borderPaint.setStrokeWidth(broderWidt);
		textRect = new Rect();
		setBackgroundDrawable(null);
		setLongClickable(false);
		setTextIsSelectable(false);
		setCursorVisible(false);


	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		isFocus = focused;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		switch (heightMode) {
			case MeasureSpec.EXACTLY:
				heightSize = MeasureSpec.getSize(heightMeasureSpec);
				break;
			case MeasureSpec.AT_MOST:
				heightSize = widthSize / numLength;
				break;
		}
		setMeasuredDimension(widthSize, heightSize);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == 67 && text.length() != 0) {
			text = text.substring(0, text.length() - 1);
			if (onInputOverListener != null) {
				onInputOverListener.onInputOver(this.text);
			}
			invalidate();
		}
		return super.onKeyDown(keyCode, event);
	}




//	rectPaint.setStrokeWidth(rectStroke);
//		textPaint.setColor(textColor);
//		textPaint.setTextSize(txtSize);
//	int width = Math.min(getMeasuredHeight(), getMeasuredWidth() / numLength);
//		for (int i = 0; i < numLength; i++) {
//		if (i <= text.length() && isFocus) {
//			rectPaint.setColor(0xff28262f);
//		} else {
//			rectPaint.setColor(0xff28262f);
//		}
////			Rect rect = new Rect(i * width + 10, 10, i * width + width - 10, width - 10);
//		Rect rect = new Rect((numLength - 1 - i) * width + widthSpace, heightSpace, (numLength - i) * width - widthSpace, width - heightSpace);
//		canvas.drawRect(rect, rectPaint);
//		list.add(rect);
//	}
//		for (int i = 0; i < text.length(); i++) {
//		textPaint.getTextBounds("0", 0, 1, textRect);
//		canvas.drawText("*", list.get(text.length() - i - 1).left + (list.get(text.length() - i - 1).right - list.get(text.length() - i - 1).left) / 2 - textRect.width() / 2,
//				list.get(i).top + ((list.get(i).bottom - list.get(i).top) / 2) + textRect.height() / 2, textPaint);
//	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!isBgFill) {
			rectPaint.setStyle(Paint.Style.STROKE);
		}
		rectPaint.setStrokeWidth(rectStroke);
		borderPaint.setColor(broderColor);
		dottedPaint.setColor(dottedColor);
		textPaint.setColor(textColor);
		textPaint.setTextSize(txtSize);

		int width = Math.min(getMeasuredHeight(), getMeasuredWidth() / numLength);







//		非阿拉伯国家
		if (!isArCountry) {
			for (int i = 0; i < numLength; i++) {
				if (i <= text.length() && isFocus) {
					rectPaint.setColor(rectChooseColor);
				} else {
					rectPaint.setColor(rectNormalColor);
				}
				Rect rect = new Rect(i * width + widthSpace, heightSpace, i * width + width - widthSpace, width - heightSpace);
				list.add(rect);
				if (isCorner) {
					Path path = new Path();
					path.moveTo(i * width + widthSpace, heightSpace + radian);
					path.quadTo(i * width + widthSpace, heightSpace, i * width + widthSpace + radian, heightSpace);
					path.lineTo(i * width + width - widthSpace - radian, heightSpace);
					path.quadTo(i * width + width - widthSpace, heightSpace, i * width + width - widthSpace, heightSpace + radian);
					path.lineTo(i * width + width - widthSpace, width - heightSpace - radian);
					path.quadTo(i * width + width - widthSpace, width - heightSpace, i * width + width - widthSpace - radian, width - heightSpace);
					path.lineTo(i * width + widthSpace + radian, width - heightSpace);
					path.quadTo(i * width + widthSpace, width - heightSpace, i * width + widthSpace, width - heightSpace - radian);
					path.lineTo(i * width + widthSpace, heightSpace + radian);
					//画出带圆角矩形的实心体
					canvas.drawPath(path, rectPaint);
					//画出带圆角矩形的边框
					canvas.drawPath(path, borderPaint);
				} else {
					//普通矩形
					canvas.drawRect(rect, rectPaint);
				}
				if (isNeedDash) {
					//最后一个画虚线
					if (i==text.length()) {
						Path dashPath = new Path();
						dashPath.moveTo(i * width + widthSpace, heightSpace);
						dashPath.lineTo(i * width + width - widthSpace, heightSpace);
						dashPath.lineTo(i * width + width - widthSpace, width - heightSpace);
						dashPath.lineTo(i * width + widthSpace, width - heightSpace);
						dashPath.lineTo(i * width + widthSpace, heightSpace);
						//画虚线
						dottedPaint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
						canvas.drawPath(dashPath, dottedPaint);
					}
				}


			}
			//画文字
			for (int i = 0; i < text.length(); i++) {
				if (isPwd) {
					switch (pwdType) {
						case CIRCLE:
							canvas.drawCircle(list.get(i).centerX(), list.get(i).centerY(), pwdType_CircleRadius, textPaint);
							break;
						case XINGHAO:
							textPaint.getTextBounds("*", 0, 1, textRect);
							int text_width = (int)textPaint.measureText("*");//测量文字的宽度
							canvas.drawText("*", list.get(i).left + (list.get(i).right - list.get(i).left) / 2 - text_width / 2,
									list.get(i).top + ((list.get(i).bottom - list.get(i).top) / 2) + textRect.height(), textPaint);
							break;
					}
				} else {
					//文字的最小矩形
					textPaint.getTextBounds(text.substring(i, i + 1), 0, 1, textRect);
					int text_width = (int)textPaint.measureText(text.substring(i, i + 1));//测量文字的宽度
//					canvas.drawText(text.substring(i, i + 1), list.get(i).left + (list.get(i).right - list.get(i).left) / 2 - textRect.width() / 2,
//							list.get(i).top + ((list.get(i).bottom - list.get(i).top) / 2) + textRect.height() / 2, textPaint);
					canvas.drawText(text.substring(i, i + 1), list.get(i).left + (list.get(i).right - list.get(i).left) / 2 - text_width / 2,
							list.get(i).top + ((list.get(i).bottom - list.get(i).top) / 2) + textRect.height() / 2, textPaint);
				}






			}
			//阿拉伯国家
		} else {
			for (int i = 0; i < numLength; i++) {
				if (i <= text.length() && isFocus) {
					rectPaint.setColor(0xff28262f);
				} else {
					rectPaint.setColor(0xff28262f);
				}
//			Rect rect = new Rect(i * width + 10, 10, i * width + width - 10, width - 10);
				Rect rect = new Rect((numLength - 1 - i) * width + widthSpace, heightSpace, (numLength - i) * width - widthSpace, width - heightSpace);
				canvas.drawRect(rect, rectPaint);





				list.add(rect);
			}
			for (int i = 0; i < text.length(); i++) {
				textPaint.getTextBounds("0", 0, 1, textRect);
				canvas.drawText("*", list.get(text.length() - i - 1).left + (list.get(text.length() - i - 1).right - list.get(text.length() - i - 1).left) / 2 - textRect.width() / 2,
						list.get(i).top + ((list.get(i).bottom - list.get(i).top) / 2) + textRect.height() / 2, textPaint);
			}
		}
	}
}





























