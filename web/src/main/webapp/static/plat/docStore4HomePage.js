$(function () {
    $('#docStore4HomePage').highcharts({
        title: {
            text: '混合图表'
        },
        xAxis: {
            categories: ['苹果', ' 橙', '梨', '香蕉', '李子']
        },
        plotOptions: {
            series: {
                stacking: 'normal'
            }
        },
        labels: {
            items: [{
                html: '水果消耗',
                style: {
                    left: '100px',
                    top: '18px',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
                }
            }]
        },
        series: [{
            type: 'column',
            name: '小张',
            data: [3, 2, 1, 3, 4]
        }, {
            type: 'column',
            name: '小潘',
            data: [2, 3, 5, 7, 6]
        }, {
            type: 'column',
            name: '小王',
            data: [4, 3, 3, 9, 0]
        }, {
            type: 'spline',
            name: '平均值',
            data: [3, 2.67, 3, 6.33, 3.33],
            marker: {
                lineWidth: 2,
                lineColor: Highcharts.getOptions().colors[3],
                fillColor: 'white'
            }
        }, {
            type: 'pie',
            name: '总的消耗',
            data: [{
                name: '小张',
                y: 13,
                color: Highcharts.getOptions().colors[0] // Jane's color
            }, {
                name: '小潘',
                y: 23,
                color: Highcharts.getOptions().colors[1] // John's color
            }, {
                name: '小王',
                y: 19,
                color: Highcharts.getOptions().colors[2] // Joe's color
            }],
            center: [100, 80],
            size: 100,
            showInLegend: false,
            dataLabels: {
                enabled: false
            }
        }]
    });
});
