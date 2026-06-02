<template>
  <div class="container">
    <h2 class="chartTitle">营业额统计</h2>
    <div class="charBox">
      <div ref="chartRef" style="width: 100%; height: 320px"></div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, onBeforeUnmount, nextTick, watch, ref } from 'vue';
import * as echarts from 'echarts';

const props = defineProps<{
  turnoverdata: {
    dateList: string[];
    turnoverList: number[];
  };
}>();

const chartRef = ref<HTMLDivElement | null>(null);
let myChart: echarts.ECharts | null = null;
const chartReady = ref(false);

const resizeChart = () => {
  if (myChart) {
    myChart.resize();
  }
};

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
    },
    grid: {
      top: '15%',
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
      data: props.turnoverdata.dateList,
    },
    yAxis: {
      type: 'value',
      min: 0,
      axisLabel: {
        color: '#666',
        fontSize: 12,
      },
    },
    legend: {
      // 对指定的data线，设置不同的legend格式
      data: ['营业额（元）'],
      bottom: '4%',
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
        name: '营业额（元）',
        type: 'line',
        smooth: false,
        showSymbol: false,
        symbolSize: 10,
        itemStyle: {
          color: '#00ccff',
        },
        lineStyle: {
          color: '#00ccff',
        },
        emphasis: {
          itemStyle: {
            color: '#fff',
            borderWidth: 5,
            borderColor: '#00ccff',
          },
        },
        areaStyle: {
          // opacity: 0.5,
          // 从上到下渐变，(0,0)是上部，(0,1)是下部
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {
              offset: 0,
              color: 'rgba(0, 221, 255, 1)'
            },
            {
              offset: 1,
              color: 'rgba(0, 221, 255, 0)'
            }
          ])
        },
        data: props.turnoverdata.turnoverList,
      },
    ],
  };

  chartInstance.setOption(option, { notMerge: true });
  chartInstance.resize();
};

watch(
  () => props.turnoverdata,
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

.legendLine {
  display: flex;
  justify-content: center;
  margin-top: 10px;

    li {
        position: relative;
        padding-left: 10px;
        /* 留出位置给红线 */
      }
    
      li::before {
        content: '';
        position: absolute;
        left: 0;
        top: 50%;
        /* 红线垂直居中 */
        transform: translateY(-50%);
        /* 红线垂直居中 */
        width: 5px;
        /* 红线宽度 */
        height: 2px;
        /* 红线高度 */
        background-color: red;
      }
}
</style>
