<template>
    <CanvasJSChart :options="options" :monthData="props.monthData" style="width: 1000px; height: 80%;" @chart-ref="chartInstance" />
</template>

<script setup>
const props = defineProps(['monthData'])
console.log(props.monthData)
const options = ref({
    theme: "light2",
    exportEnabled: true,
    title: {
        text: "月度成交数/中介费"
    },
    axisY: {
        title: ["成交数", "中介费"]
    },
    toolTip: {
        shared: true
    },
    legend: {
        cursor: "pointer",
        itemclick: function (e) {
            if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
                e.dataSeries.visible = false;
            } else {
                e.dataSeries.visible = true;
            }
            e.chart.render();
        }
    },
    data: [{
        type: "spline",
        name: "成交数",
        showInLegend: true,
        color: "#012066",
        dataPoints: props.monthData[0]
    }, {
        type: "spline",
        name: "中介费",
        showInLegend: true,
        color: "#F7C705",
        dataPoints: props.monthData[1]
    }]
})

const chart = ref()
function chartInstance(chart) {
    chart.value = chart;
}

</script>
