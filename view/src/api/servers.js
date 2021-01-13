import {get, post, del, put} from '@/common/http';

export default {
    listServerGroups(params, configs) {
        return get('/api/server/group', params, configs);
    },
    addServerGroup(params) {
        return put('/api/server/group', params);
    },
    addServer(params, configs) {
        return put('/api/server', params, configs);
    },
    deleteServer(serverId) {
        return del(`/api/server/${serverId}`);
    },
    updateServer(serverId, params) {
        return post(`/api/server/${serverId}`, params);
    },
    pageListServers(params) {
        return get('/api/server', params)
    }
}
