<template>
  <div class="container">
    <h2 class="chartTitle">销量排名TOP10</h2>
    <div class="charBox">
      <div ref="chartRef" style="width: 100%; height: 300px"></div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, onBeforeUnmount, nextTick, watch, ref } from 'vue';
import * as echarts from 'echarts';

interface Top10Data {
  nameList: string[];
  numberList: number[];
}

const props = defineProps<{
  top10data: Top10Data;
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
      top: '5%',
      left: '6%',
      right: '10%',
      bottom: '10%',
      containLabel: true,
    },
    xAxis: {
      show: false,
    },
    yAxis: {
      axisLine: {
        show: false,
      },
      axisTick: {
        show: false,
        alignWithLabel: true,
      },
      type: 'category',
      axisLabel: {
        color: '#666',
        fontSize: 12,
      },
      data: props.top10data.nameList,
    },
    series: [
      {
        data: props.top10data.numberList,
        type: 'bar',
        showBackground: true,
        backgroundStyle: {
          color: '#F3F4F7',
        },
        barWidth: 20,
        barGap: '80%',
        barCategoryGap: '80%',
        itemStyle: {
          borderRadius: [0, 10, 10, 0],
          color: new echarts.graphic.LinearGradient(
            1,0,
            0,0,
            [
              { offset: 0, color: '#00aaff' },
              { offset: 1, color: '#22ccff' },
            ]
          ),
        },
        emphasis: {
          itemStyle: {
            borderRadius: 30,
          },
        },
        label: {
          show: true,
          formatter: '{@score}',
          color: '#333',
          position: ['8', '5'],
        },
      },
    ],
  };
  chartInstance.setOption(option, { notMerge: true });
  chartInstance.resize();
};

watch(
  () => props.top10data,
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
