<template>
    <ul class="el-list-menu">
        <slot></slot>
    </ul>
</template>

<script>

    export default {
        name: 'list-menu',
        componentName: 'list-menu',
        props: {
            textColor: {
                type: String,
                default: '',
            },
            activeTextColor: {
                type: String,
                default: ''
            },
            activeBackgroundColor: {
                type: String,
                default: ''
            },
            hoverBackgroundColor: {
                type: String,
                default: '',
            },
            defaultActive: {
                type: String,
                default: ''
            },
            router: Boolean
        },
        data() {
            return {
                activeIndex: this.defaultActive,
                items: {}
            }
        },
        methods: {
            handleItemClick(item) {
                const { index } = item;
                const oldActiveIndex = this.activeIndex;
                const hasIndex = item.index !== null;
                if (hasIndex) {
                    this.activeIndex = item.index;
                }
                this.$emit('select', index, item);
                if(this.router && hasIndex) {
                    let route = index || item.route;
                    try {
                        this.$router.push(route, () => {}, (error) => {
                            this.activeIndex = oldActiveIndex;
                            if(error) {
                                console.error(error);
                            }
                        });
                    } catch (e) {
                        console.error(e);
                    }
                }
            },
            addItem(item) {
                this.$set(this.items, item.index, item);
            },
            removeItem(item) {
                delete this.items[item.index];
            }
        },
        mounted() {
            this.$on('item-click', this.handleItemClick);
        },
        watch: {
            defaultActive(val) {
                const item = this.items[val] || this.items[this.activeIndex] || this.items[this.defaultActive];
                if(item) {
                    this.activeIndex = item.index;
                } else {
                    this.activeIndex = null;
                }
            }
        },
        provide() {
            return {
                rootMenu: this
            }
        }
    }

</script>

<style>
    ul, li {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .el-list-menu {
        list-style: none;
        position: relative;
    }
</style>
