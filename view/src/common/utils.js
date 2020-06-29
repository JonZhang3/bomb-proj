const jsmd5 = require('js-md5');

export default {
    md5(message) {
        return jsmd5(message);
    },
    isArray(src) {
        return Object.prototype.toString.call(src) === '[object Array]';
    }
}
