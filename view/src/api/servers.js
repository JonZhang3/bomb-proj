import {get, post, del, put} from '@/common/http';
import settings from '../common/settings';

const baseUrl = settings.baseUrl;

export default {
    listServerGroups(params, configs) {
        return get('/api/server/group', params, configs);
    },
    addServerGroup(params) {
        return put('api/server/group', params)
    }
}
