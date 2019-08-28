// java org.mozilla.javascript.tools.jsc.Main -opt 9 -version 170 -nosource test2.js
let cnt = 0;

function hello() {
    cnt++;
    app_activity.toast("hello, this is test2.js not test2.class, cnt: " + cnt + ", s: " + new Date().getSeconds());
}

function hello2() {
    hello();
    setTimeout(hello, 5000);

    java.lang.Thread.sleep(2000);
    app_activity.toast("exec test2.hello in 3s later, s: " + new Date().getSeconds());
}

hello();

exports.hello2 = hello2;
