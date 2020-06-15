<template>
  <div class="ToDoList">
    <!-- 在组件模板的标题h1中，我们使用v-text命令绑定title，这样就会自动替换原本html文本,显示了title。  -->
    <h1 v-text="title">My ToDoList</h1>

    <!-- v-html会自动替换成html -->
    <h1 v-html="text">My ToDoList html</h1>
    <ul>
      <!--vue中列表循环需加:key="唯一标识" 唯一标识可以是item里面id、index等，因为vue组件高度复用，增加Key可以标识组件的唯一性。
      为了更好地区别各个组件， key的作用主要是为了高效的更新虚拟DOM。-->
      <li v-for="item in items" :key="item.id">
        <span v-text="item.title"></span>
        <button v-on:click="toggleFinish(item)">完成</button>
      </li>
    </ul>
    <!-- v-model是数据双向绑定 即v-model标签与newItem对象两者是相互改变，改变其中一者另一方也会改变 -->
    <input type="text" v-model="newItem">
    <button @click="addNewItem">添加</button>
  </div>
</template>

<script>
export default {
  name: 'ToDoList',
  data () {
    return {
      title: 'ToDoLis Haha gaga',
      text: '<span>嘎嘎</span>',
      id: 0,
      items: [
      ],
      newItem: '' // 输入框内容默认为空
    }
  },
  methods: {
    toggleFinish (recycleItem) {
      this.items.map((item, index) => {
        if (item.id === recycleItem.id) {
          this.items.splice(index, 1) // 从数组中删除元素
        }
      })
    },
    addNewItem () {
      this.items.push({
        id: this.id,
        title: this.newItem
      })
      this.newItem = ''
      this.id++
    }
  }
}
</script>
