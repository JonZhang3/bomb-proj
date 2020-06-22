const permissions = {
    modInter: '修改接口',
    addInter: '添加接口',
    delInter: '删除接口',

    modModule: '修改模块',
    addModule: '添加模块',
    delModule: '删除模块',

    modCode: '修改状态码',
    addError: '添加状态码',
    delError: '删除状态码',

    modArticle: '修改文档',
    addArticle: '添加文档',
    delArticle: '删除文档',

    modBug: '修改缺陷',
    addBug: '添加缺陷',
    delBug: '删除缺陷',

    modSource: '修改文件',
    addSource: '添加文件',
    delSource: '删除文件',

    modEnv: '修改项目环境',
    addEnv: '添加项目环境',
    delEnv: '删除项目环境'
}

export default {
    getName(key) {
        const val = permissions[key];
        return val ? val : '';
    },

    getShowHtml(keysStr) {
        if(keysStr) {
            const names = [];
            const keys = keysStr.split(',');
            if(keys) {
                for(let i = 0, len = keys.length; i < len; i++) {
                    const name = this.getName(keys[i]);
                    if(name) {
                        names.push(name);
                    }
                }
            }
            return html.join('');
        }
        return [];
    }
}
