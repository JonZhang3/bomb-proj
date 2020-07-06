import setting from '../settings';

export default {
    mounted() {
        document.title = this.documentTitle ? `${this.documentTitle} - ${setting.title}` : setting.title;
    }
}
