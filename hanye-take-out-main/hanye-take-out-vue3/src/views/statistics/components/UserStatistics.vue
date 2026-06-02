<template>
  <div class="container">
    <h2 class="chartTitle">用户统计</h2>
    <div class="charBox">
      <div ref="chartRef" style="width: 100%; height: 320px"></div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, onBeforeUnmount, nextTick, watch, ref } from 'vue';
import * as echarts from 'echarts';

interface UserData {
  dateList: string[];
  totalUserList: number[];
  newUserList: number[];
}

const props = defineProps<{
  userdata: UserData;
}>();

let myChart: echarts.ECharts | null = null;
const chartReady = ref(false);

const resizeChart = () => {
  if (myChart) {
    myChart.resize();
  }
};

const chartRef = ref<HTMLDivElement | null>(null);

const getChartInstance = () => {
  const chartDom = chartRef.value;
  if (!chartDom) return null;
  if (chartDom.clientWidth === 0 || chartDom.clientHeight === 0) {
    return null;
  }
  if (myChart) return myChart;
  myChart = echarts.init(chartDom);
  return myChart;
};

const initChart = async () => {
  if (!chartRef.value) {
    await nextTick();
  }
  const chart = getChartInstance();
  if (!chart) {
    await nextTick();
  }
  const chartInstance = getChartInstance();
  if (!chartInstance) return;
  const option = {
    tooltip: {
      trigger: 'axis',
      backgroundColor: '#fff',
      borderRadius: 2,
      textStyle: {
        color: '#333',
        fontSize: 12,
        fontWeight: 300,
      },
    },
    grid: {
      top: '20%',
      left: '6%',
      right: '10%',
      bottom: '12%',
      containLabel: true,
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      axisLabel: {
        color: '#666',
        fontSize: 12,
      },
      axisLine: {
        lineStyle: {
          color: '#E5E4E4',
          width: 1,
        },
      },
      data: props.userdata.dateList,
    },
    yAxis: [
      {
        type: 'value',
        min: 0,
        axisLabel: {
          color: '#666',
          fontSize: 12,
        },
      },
    ],
    legend: {
      // 对指定的data线，设置不同的legend格式
      // data: ['用户总量（个）', '新增用户（个）'],
      bottom: '0%',
      icon: 'rect',
      itemWidth: 20,
      itemHeight: 2,
      textStyle: {
        fontSize: 12,
        color: '#666'
      }
    },
    series: [
      {
        name: '用户总量（个）',
        type: 'line',
        smooth: false,
        showSymbol: false,
        symbolSize: 10,
        itemStyle: {
          color: '#FFD000',
        },
        lineStyle: {
          color: '#FFD000',
        },
        emphasis: {
          itemStyle: {
            color: '#fff',
            borderWidth: 5,
            borderColor: '#FFC100',
          },
        },
        // areaStyle: {
        //   // opacity: 0.5,
        //   // 从上到下渐变，(0,0)是上部，(0,1)是下部
        //   color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
        //     {
        //       offset: 0,
        //       color: 'rgba(255, 221, 0, 1)'
        //     },
        //     {
        //       offset: 1,
        //       color: 'rgba(255, 221, 0, 0)'
        //     }
        //   ])
        // },
        data: props.userdata.totalUserList,
      },
      {
        name: '新增用户（个）',
        type: 'line',
        smooth: false,
        showSymbol: false,
        symbolSize: 10,
        itemStyle: {
          color: '#FD7F7F',
        },
        lineStyle: {
          color: '#FD7F7F',
        },
        emphasis: {
          itemStyle: {
            color: '#fff',
            borderWidth: 5,
            borderColor: '#FD7F7F',
          },
        },
        areaStyle: {
          // opacity: 0.5,
          // 从上到下渐变，(0,0)是上部，(0,1)是下部
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {
              offset: 0,
              color: 'rgba(255, 1, 0, 1)'
            },
            {
              offset: 1,
              color: 'rgba(255, 1, 0, 0)'
            }
          ])
        },
        data: props.userdata.newUserList,
      },
    ],
  };
  chartInstance.setOption(option, { notMerge: true });
  chartInstance.resize();
};

watch(
  () => props.userdata,
  () => {
    if (chartReady.value) {
      initChart();
    }
  },
  { immediate: true, deep: true, flush: 'post' }
);

onMounted(() => {
  chartReady.value = true;
  initChart();
  window.addEventListener('resize', resizeChart);
  nextTick(() => {
    resizeChart();
  });
});

onBeforeUnmount(() => {
  if (myChart) {
    myChart.dispose();
    myChart = null;
  }
  window.removeEventListener('resize', resizeChart);
});
</script>

<style lang="less" scoped>
.chartTitle {
  font-size: 16px;
  color: #333;
  margin: 10px 20px;
}
</style>