let cnt = 0;

function hello() {
    cnt++;
    app_activity.toast("hello, this is test.js, cnt: " + cnt);
}

hello();

exports.hello = hello;
