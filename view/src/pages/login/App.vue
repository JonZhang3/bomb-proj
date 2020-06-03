<template>
    <div id="app">
<!--        <router-view></router-view>-->
        <div class="circular circular-1"></div>
        <div class="circular circular-2"></div>
        <div class="circular circular-3"></div>
        <div class="circular circular-4"></div>
        <div class="circular circular-5"></div>
        <div class="circular circular-6"></div>
        <div class="container">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'app',
    }
</script>

<style>
    #app {
        position: relative;
        height: 100%;
        background-color: #222F4F;
    }
    .circular {
        position: absolute;
        opacity: .3;
        border-radius: 50%;
        z-index: 1;
    }
    .circular-1 {
        width: 78px;
        height: 78px;
        left: 160px;
        top: 108px;
        background: linear-gradient(-90deg,#00d5c8,#00b481);
    }
    .circular-2 {
        width: 60px;
        height: 60px;
        left: 260px;
        top: 260px;
        background: linear-gradient(0deg,#FF6400,#ffee80);
    }
    .circular-3 {
        width: 135px;
        height: 135px;
        right: 432px;
        top: 50px;
        background: linear-gradient(0deg,#ff5a25,#ff005a);
    }
    .circular-4 {
        width: 25px;
        height: 25px;
        right: 278px;
        top: 41px;
        background: linear-gradient(-90deg,#bd4c86,#c864ae);
    }
    .circular-5 {
        width: 30px;
        height: 30px;
        right: 100px;
        top: 100px;
        background: linear-gradient(-90deg, #bd4c86, #c864ae);
    }
    .circular-6 {
        width: 90px;
        height: 90px;
        right: 0;
        top: 95px;
        background: linear-gradient(-90deg,#271e5b,#0d76ef);
    }
    .container {
        margin: 0 auto;
        padding-top: 100px;
    }
</style>
