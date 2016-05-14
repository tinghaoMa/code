package com.mth.textview_spannabledemo;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.DrawableMarginSpan;
import android.text.style.EasyEditSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 常用的就是上述的四个值，这里我们来分别解释以下:
 *  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)[]
 *  Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)[)
 *  Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)(]
 *  Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)()
 */
public class MainActivity extends Activity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.id_textview);
//        setText();
//        setSpan();
//        setBCS();
//        setBSpan();
//        setCS();  //点击事件
//        setDMSpan();
//        setEdit();
        setfCS();
    }

    /**
     * 设置背景
     */
    private void setBCS() {
        String source1 = "#重磅消息#近日谷歌放出Android N的第二个开发者预览版(Developer Preview)";
        SpannableString span = new SpannableString(source1);
        span.setSpan(new BackgroundColorSpan(Color.RED), 0, source1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(span);
    }

    /**
     * 添加一个项目符号 例如 .
     */
    private void setBSpan() {
        final String source3 = "近日谷歌放出Android N的第二个开发者预览版";
        SpannableString bSpan = new SpannableString(source3);
        bSpan.setSpan(new BulletSpan(), 0, source3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(bSpan);
    }

    /**
     * 设置文本点击事件
     */
    private void setCS() {
        final String source2 = "(Developer Preview)";
        SpannableString clickSpan = new SpannableString(source2);
        clickSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(MainActivity.this, source2, Toast.LENGTH_SHORT).show();
            }
        }, 0, source2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(clickSpan);
        textView.setMovementMethod(LinkMovementMethod.getInstance());//这句话必须加上
    }

    /**
     * DrawableMarginSpan 可以设置一个图标，并且可以设置与文字的宽度
     */
    private void setDMSpan() {
        final String source3 = "(Developer Preview)";
        SpannableString dmSpan = new SpannableString(source3);
        dmSpan.setSpan(new DrawableMarginSpan(getResources().getDrawable(R.mipmap.ic_launcher), 100), 0, source3
                .length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(dmSpan);
    }

    /**
     * EasyEditSpan 当文本改变或者删除时调用, 例如入下长按可以很容易删除一行
     */
    private void setEdit() {
        textView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        textView.setSingleLine(false);
        textView.setText("近日\n谷歌放出Android N的\n第二个开发者预览版");
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Layout layout = textView.getLayout();
                final int line = layout.getLineForOffset(textView.getSelectionStart());
                final int start = layout.getLineStart(line);
                final int end = layout.getLineEnd(line);
                textView.getEditableText().setSpan(new EasyEditSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                return true;
            }
        });
    }

    /**
     * ForegroundColorSpan 设置文字前景色
     */
    private void setfCS() {
        String source1 = "#重磅消息#近日谷歌放出Android N的第二个开发者预览版(Developer Preview)";
        SpannableString span = new SpannableString(source1);
        span.setSpan(new ForegroundColorSpan(Color.RED), 10, source1.length()/2+5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(span);
    }

    private void setText() {
        String originText = "#重磅消息#近日谷歌放出Android N的第二个开发者预览版(Developer Preview)";

        String effect1 = "<font color='#FF0000'>#重磅消息#</font> <br> 近日谷歌放出Android " +
                "N的第二个开发者预览版<a href='http://www.baidu.com'>(Developer Preview)</a>";

        String effect2 = "<font color='#303F9F'>#重磅消息#</font> 近日谷歌放出Android " +
                "N的第二个开发者预览版<a href='http://www.baidu.com'>(Developer Preview)</a>";

        StringBuilder sb = new StringBuilder(originText);
        sb.append("<br><br><br><br>");
        sb.append(effect1);
        sb.append("<br><br><br><br>");
        sb.append(effect2);
        textView.setText(Html.fromHtml(sb.toString()));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }


    private void setSpan() {
        String originText = "#重磅消息#近日谷歌放出Android N的第二个开发者预览版(Developer Preview)";
        SpannableStringBuilder sb = new SpannableStringBuilder(originText);
        sb.append("\r\n").append("\r\n").append("\r\n");
        getEffect1Span(sb);
        sb.append("\r\n").append("\r\n").append("\r\n");
        getEffect2Span(sb);
        textView.setText(sb);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void getEffect1Span(SpannableStringBuilder builder) {
        String source1 = "#重磅消息#";
        SpannableString span = new SpannableString(source1);
        span.setSpan(new ForegroundColorSpan(Color.BLUE), 0, source1.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(span);
        builder.append("\n");
        String source2 = "近日谷歌放出Android N的第二个开发者预览版";
        builder.append(source2);

        final String source3 = "(Developer Preview)";
        SpannableString clickSpan = new SpannableString(source3);
        clickSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(MainActivity.this, source3, Toast.LENGTH_SHORT).show();
            }
        }, 0, source3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(clickSpan);
    }

    private void getEffect2Span(SpannableStringBuilder builder) {
        String source1 = "#重磅消息#近日谷歌放出Android N的第二个开发者预览版";
        SpannableString span = new SpannableString(source1);
        span.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, 6, Spanned
                .SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(span);

        final String source2 = "(Developer Preview)";
        SpannableString clickSpan = new SpannableString(source2);
        clickSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(MainActivity.this, source2, Toast.LENGTH_SHORT).show();
            }
        }, 0, source2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(clickSpan);
    }
}
