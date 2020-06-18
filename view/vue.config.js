const utils = require('./build/utils');

module.exports = {
    pages: utils.setPages(),
    devServer: {
        port: 8082
    }
};
