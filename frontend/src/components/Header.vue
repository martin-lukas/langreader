<template>
  <div id="header">
    LangReader
  </div>
</template>

<style scoped>
  #header {
    text-align: center;
    font-family: 'Dancing Script', cursive;
    font-size: 40px;
    padding: 5px 0 10px 0;
    color: var(--active-el-color);
    background-color: #333;
    user-select: none;
    text-shadow: 2px 1px var(--active-el-color-dark);
  }

  @media screen and (min-width: 500px) {
    #header {
      font-size: 60px;
    }
  }
</style>