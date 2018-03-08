<template>
<div class="ui active inverted dimmer" v-if="loading">
    <div class="loader-container">
        <div class="ui text loader"></div>
    </div>
</div>
</template>

<script>
export default{
    props: {
        loading: Boolean,
    }
};
</script>

<style scoped>
div.ui.active.dimmer {
    position: fixed;
    padding: 60px 0 0 180px;
    z-index: 29;
}
.loader-container {
    position: relative;
    height: 100%;
}
.loader-container>.ui.text.loader {
    z-index: 29;
}
</style>
