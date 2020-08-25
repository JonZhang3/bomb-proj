<template>
    <div class="bomb-label-list-item">
        <div class="bomb-label-list-item__label" :style="labelStyle">{{label}}</div>
        <div class="bomb-label-list-item__content">
            <slot></slot>
        </div>
    </div>
</template>

<script>

    export default {
        name: 'LabelListItem',
        inject: ['root'],
        props: {
            label: String
        },
        data() {
            return {
                labelWidth: this.root.labelWidth
            }
        },
        computed: {
            labelStyle() {
                const styles = {};
                if(this.labelWidth) {
                    styles.width = this.labelWidth;
                } else {
                    styles.width = '80px';
                }
                return styles;
            }
        }
    }

</script>
