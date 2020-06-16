const jsmd5 = require('js-md5');

function md5(message) {
    return jsmd5(message);
}

export default {
    md5
}
