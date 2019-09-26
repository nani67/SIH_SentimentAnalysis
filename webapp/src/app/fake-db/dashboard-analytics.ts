export class AnalyticsDashboardDb
{
    public static widgets = {
        widget1: {
            chartType: 'line',
            datasets : {
                2016: [
                    {
                        label: 'Datapoints',
                        data : [1120, 920, 824, 732, 943, 1037, 1400, 556],
                        fill : 'start'

                    }
                ],
                2017: [
                    {
                        label: 'Datapoints',
                        data : [1592, 1256, 1306, 100, 890, 903, 950, 890],
                        fill : 'start'

                    }
                ],
                2018: [
                    {
                        label: 'Datapints',
                        data : [920, 910, 350, 1240, 930, 560, 55, 250],
                        fill : 'start'

                    }
                ]

            },
            labels   : ['AUG W1', 'AUG W2', 'AUG W3', 'AUG W4', 'SEP W1', 'SEP W2', 'SEP W3', 'SEP W4'],
            colors   : [
                {
                    borderColor              : '#42a5f5',
                    backgroundColor          : '#42a5f5',
                    pointBackgroundColor     : '#1e88e5',
                    pointHoverBackgroundColor: '#1e88e5',
                    pointBorderColor         : '#ffffff',
                    pointHoverBorderColor    : '#ffffff'
                }
            ],
            options  : {
                spanGaps           : false,
                legend             : {
                    display: false
                },
                maintainAspectRatio: false,
                layout             : {
                    padding: {
                        top  : 32,
                        left : 32,
                        right: 32
                    }
                },
                elements           : {
                    point: {
                        radius          : 4,
                        borderWidth     : 2,
                        hoverRadius     : 4,
                        hoverBorderWidth: 2
                    },
                    line : {
                        tension: 0
                    }
                },
                scales             : {
                    xAxes: [
                        {
                            gridLines: {
                                display       : false,
                                drawBorder    : false,
                                tickMarkLength: 18
                            },
                            ticks    : {
                                fontColor: '#ffffff'
                            }
                        }
                    ],
                    yAxes: [
                        {
                            display: false,
                            ticks  : {
                                min     : 0,
                                max     : 1500,
                                stepSize: 1
                            }
                        }
                    ]
                },
                plugins            : {
                    filler      : {
                        propagate: false
                    },
                    xLabelsOnTop: {
                        active: true
                    }
                }
            }
        },
        widget2: {
            conversion: {
                value   : 25,
                ofTarget: 5
            },
            chartType : 'bar',
            datasets  : [
                {
                    label: 'Conversion',
                    data : [10, 11, 15, 20, 25]
                }
            ],
            labels    : ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
            colors    : [
                {
                    borderColor    : '#42a5f5',
                    backgroundColor: '#42a5f5'
                }
            ],
            options   : {
                spanGaps           : false,
                legend             : {
                    display: false
                },
                maintainAspectRatio: false,
                layout             : {
                    padding: {
                        top   : 24,
                        left  : 16,
                        right : 16,
                        bottom: 16
                    }
                },
                scales             : {
                    xAxes: [
                        {
                            display: false
                        }
                    ],
                    yAxes: [
                        {
                            display: false,
                            ticks  : {
                                min: 0,
                                max: 50
                            }
                        }
                    ]
                }
            }
        },
        widget3: {
            impressions: {
                value   : '18',
                ofTarget: 72
            },
            chartType  : 'line',
            datasets   : [
                {
                    label: 'Impression',
                    data : [67000, 54000, 82000, 57000, 72000, 57000, 87000, 72000, 89000, 98700, 112000, 136000, 110000, 149000, 98000],
                    fill : false
                }
            ],
            labels     : ['Jan 1', 'Jan 2', 'Jan 3', 'Jan 4', 'Jan 5', 'Jan 6', 'Jan 7', 'Jan 8', 'Jan 9', 'Jan 10', 'Jan 11', 'Jan 12', 'Jan 13', 'Jan 14', 'Jan 15'],
            colors     : [
                {
                    borderColor: '#5c84f1'
                }
            ],
            options    : {
                spanGaps           : false,
                legend             : {
                    display: false
                },
                maintainAspectRatio: false,
                elements           : {
                    point: {
                        radius          : 2,
                        borderWidth     : 1,
                        hoverRadius     : 2,
                        hoverBorderWidth: 1
                    },
                    line : {
                        tension: 0
                    }
                },
                layout             : {
                    padding: {
                        top   : 24,
                        left  : 16,
                        right : 16,
                        bottom: 16
                    }
                },
                scales             : {
                    xAxes: [
                        {
                            display: false
                        }
                    ],
                    yAxes: [
                        {
                            display: false,
                            ticks  : {
                                // min: 100,
                                // max: 500
                            }
                        }
                    ]
                }
            }
        },
        widget4: {
            visits   : {
                value   : 5,
                ofTarget: 90
            },
            chartType: 'bar',
            datasets : [
                {
                    label: 'Treated',
                    data : [1, 2, 2, 3, 4]
                }
            ],
            labels   : ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
            colors   : [
                {
                    borderColor    : '#f44336',
                    backgroundColor: '#f44336'
                }
            ],
            options  : {
                spanGaps           : false,
                legend             : {
                    display: false
                },
                maintainAspectRatio: false,
                layout             : {
                    padding: {
                        top   : 24,
                        left  : 16,
                        right : 16,
                        bottom: 16
                    }
                },
                scales             : {
                    xAxes: [
                        {
                            display: false
                        }
                    ],
                    yAxes: [
                        {
                            display: false,
                            ticks  : {
                                min: 0,
                                max: 10
                            }
                        }
                    ]
                }
            }
        },
        widget5: {
            chartType: 'line',
            datasets : {
                yesterday: [
                    {
                        label: 'Visitors',
                        data : [190, 300, 340, 220, 290, 390, 250, 380, 410, 380, 320, 290],
                        fill : 'start'

                    },
                    {
                        label: 'Page views',
                        data : [2200, 2900, 3900, 2500, 3800, 3200, 2900, 1900, 3000, 3400, 4100, 3800],
                        fill : 'start'
                    }
                ],
                today    : [
                    {
                        label: 'Visitors',
                        data : [410, 380, 320, 290, 190, 390, 250, 380, 300, 340, 220, 290],
                        fill : 'start'
                    },
                    {
                        label: 'Page Views',
                        data : [3000, 3400, 4100, 3800, 2200, 3200, 2900, 1900, 2900, 3900, 2500, 3800],
                        fill : 'start'

                    }
                ]
            },
            labels   : ['12am', '2am', '4am', '6am', '8am', '10am', '12pm', '2pm', '4pm', '6pm', '8pm', '10pm'],
            colors   : [
                {
                    borderColor              : '#3949ab',
                    backgroundColor          : '#3949ab',
                    pointBackgroundColor     : '#3949ab',
                    pointHoverBackgroundColor: '#3949ab',
                    pointBorderColor         : '#ffffff',
                    pointHoverBorderColor    : '#ffffff'
                },
                {
                    borderColor              : 'rgba(30, 136, 229, 0.87)',
                    backgroundColor          : 'rgba(30, 136, 229, 0.87)',
                    pointBackgroundColor     : 'rgba(30, 136, 229, 0.87)',
                    pointHoverBackgroundColor: 'rgba(30, 136, 229, 0.87)',
                    pointBorderColor         : '#ffffff',
                    pointHoverBorderColor    : '#ffffff'
                }
            ],
            options  : {
                spanGaps           : false,
                legend             : {
                    display: false
                },
                maintainAspectRatio: false,
                tooltips           : {
                    position : 'nearest',
                    mode     : 'index',
                    intersect: false
                },
                layout             : {
                    padding: {
                        left : 24,
                        right: 32
                    }
                },
                elements           : {
                    point: {
                        radius          : 4,
                        borderWidth     : 2,
                        hoverRadius     : 4,
                        hoverBorderWidth: 2
                    }
                },
                scales             : {
                    xAxes: [
                        {
                            gridLines: {
                                display: false
                            },
                            ticks    : {
                                fontColor: 'rgba(0,0,0,0.54)'
                            }
                        }
                    ],
                    yAxes: [
                        {
                            gridLines: {
                                tickMarkLength: 16
                            },
                            ticks    : {
                                stepSize: 1000
                            }
                        }
                    ]
                },
                plugins            : {
                    filler: {
                        propagate: false
                    }
                }
            }
        },
        widget6: {
            markers: [
                {
                    lat  : 52,
                    lng  : -73,
                    label: '120'
                },
                {
                    lat  : 37,
                    lng  : -104,
                    label: '498'
                },
                {
                    lat  : 21,
                    lng  : -7,
                    label: '443'
                },
                {
                    lat  : 55,
                    lng  : 75,
                    label: '332'
                },
                {
                    lat  : 51,
                    lng  : 7,
                    label: '50'
                },
                {
                    lat  : 31,
                    lng  : 12,
                    label: '221'
                },
                {
                    lat  : 45,
                    lng  : 44,
                    label: '455'
                },
                {
                    lat  : -26,
                    lng  : 134,
                    label: '231'
                },
                {
                    lat  : -9,
                    lng  : -60,
                    label: '67'
                },
                {
                    lat  : 33,
                    lng  : 104,
                    label: '665'
                }
            ],
            styles : [
                {
                    featureType: 'administrative',
                    elementType: 'labels.text.fill',
                    stylers    : [
                        {
                            color: '#444444'
                        }
                    ]
                },
                {
                    featureType: 'landscape',
                    elementType: 'all',
                    stylers    : [
                        {
                            color: '#f2f2f2'
                        }
                    ]
                },
                {
                    featureType: 'poi',
                    elementType: 'all',
                    stylers    : [
                        {
                            visibility: 'off'
                        }
                    ]
                },
                {
                    featureType: 'road',
                    elementType: 'all',
                    stylers    : [
                        {
                            saturation: -100
                        },
                        {
                            lightness: 45
                        }
                    ]
                },
                {
                    featureType: 'road.highway',
                    elementType: 'all',
                    stylers    : [
                        {
                            visibility: 'simplified'
                        }
                    ]
                },
                {
                    featureType: 'road.arterial',
                    elementType: 'labels.icon',
                    stylers    : [
                        {
                            visibility: 'off'
                        }
                    ]
                },
                {
                    featureType: 'transit',
                    elementType: 'all',
                    stylers    : [
                        {
                            visibility: 'off'
                        }
                    ]
                },
                {
                    featureType: 'water',
                    elementType: 'all',
                    stylers    : [
                        {
                            color: '#039be5'
                        },
                        {
                            visibility: 'on'
                        }
                    ]
                }
            ]
        },
        widget7: {
            scheme : {
                domain: ['#4867d2', '#5c84f1', '#89a9f4']
            },
            devices: [
                {
                    name  : 'Instagram',
                    value : 48.8,
                    change: -0.6
                },
                {
                    name  : 'Facebook',
                    value : 34.8,
                    change: 0.7
                },
                {
                    name  : 'Twitter',
                    value : 16.4,
                    change: 0.1
                }
            ]
        },
        widget8: {
            scheme : {
                domain: ['#5c84f1']
            },
            today  : '12,540',
            change : {
                value     : 321,
                percentage: 2.05
            },
            data   : [
                {
                    name  : 'Sales',
                    series: [
                        {
                            name : 'Jan 1',
                            value: 540
                        },
                        {
                            name : 'Jan 2',
                            value: 539
                        },
                        {
                            name : 'Jan 3',
                            value: 538
                        },
                        {
                            name : 'Jan 4',
                            value: 539
                        },
                        {
                            name : 'Jan 5',
                            value: 540
                        },
                        {
                            name : 'Jan 6',
                            value: 539
                        },
                        {
                            name : 'Jan 7',
                            value: 540
                        }
                    ]
                }
            ],
            dataMin: 538,
            dataMax: 541
        },
        widget9: {
            rows: [
                {
                    title     : 'Holiday Travel',
                    clicks    : 3621,
                    conversion: 90
                },
                {
                    title     : 'Get Away Deals',
                    clicks    : 703,
                    conversion: 7
                },
                {
                    title     : 'Airfare',
                    clicks    : 532,
                    conversion: 0
                },
                {
                    title     : 'Vacation',
                    clicks    : 201,
                    conversion: 8
                },
                {
                    title     : 'Hotels',
                    clicks    : 94,
                    conversion: 4
                }
            ]
        },
        widget11     : {
            title: 'At Risk Students',
            table: {
                columns: ['avatar', 'name', 'department', 'risk_level', 'tag_source', 'confidence'],
                rows   : [
                    {
                        avatar  : 'assets/images/avatars/james.jpg',
                        name    : 'Rashmi R',
                        department: 'BE/IS/2020',
                        risk_level  : 'HIGH',
                        tag_source   : 'Assistant, Classroom',
                        confidence   : '85.6%'
                    },
                    {
                        avatar  : 'assets/images/avatars/katherine.jpg',
                        name    : 'Aishwarya Kedia',
                        department: 'BE/CS/2020',
                        risk_level  : 'HIGH',
                        tag_source   : 'Instagram',
                        confidence   : '81.6%'
                    },
                    {
                        avatar  : 'assets/images/avatars/andrew.jpg',
                        name    : 'Ashish S',
                        department: 'BE/EC/2021',
                        risk_level  : 'MED',
                        tag_source   : 'Instagram',
                        confidence   : '80.6%'
                    },
                    {
                        avatar  : 'assets/images/avatars/jane.jpg',
                        name    : 'Lucy H',
                        department: 'MS/CSA1/2022',
                        risk_level  : 'MED',
                        tag_source   : 'Classroom',
                        confidence   : '91.6%'
                    },
                    {
                        avatar  : 'assets/images/avatars/alice.jpg',
                        name    : 'Madhumita G',
                        department: 'BE/IS/2022',
                        risk_level  : 'MED',
                        tag_source   : 'Instagram',
                        confidence   : '70.6%'
                    },
                    {
                        avatar  : 'assets/images/avatars/vincent.jpg',
                        name    : 'Howard KS',
                        department: 'BE/CIV/2022',
                        risk_level  : 'MED',
                        tag_source   : 'Facebook Graph',
                        confidence   : '68.6%'
                    },
                    {
                        avatar  : 'assets/images/avatars/joyce.jpg',
                        name    : 'Anusha S',
                        department: 'BE/ME/2020',
                        risk_level  : 'MED',
                        tag_source   : 'Assistant',
                        confidence   : '82.6%'
                    },
                    {
                        avatar  : 'assets/images/avatars/danielle.jpg',
                        name    : 'Dorothy Morris',
                        department: 'BE/ME/2020',
                        risk_level  : 'LOW',
                        tag_source   : 'Instagram',
                        confidence   : '87.3%'
                    },
                    // {
                    //     avatar  : 'assets/images/avatars/carl.jpg',
                    //     name    : 'Bugs Bunny',
                    //     department: 'BE/EE/2020',
                    //     risk_level  : 'LOW',
                    //     tag_source   : 'Assistant',
                    //     confidence   : '75.3%'
                    // },
                    // {
                    //     avatar  : 'assets/images/avatars/profile.jpg',
                    //     name    : 'Catherine Rogers',
                    //     department: 'BE/CIV/2020',
                    //     risk_level  : 'LOW',
                    //     tag_source   : 'Instagram',
                    //     confidence   : '82.6%'
                    // },
                ]
            }
        }
    };

}
