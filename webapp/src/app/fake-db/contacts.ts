export class ContactsFakeDb
{
    public static contacts = [
        {
            id      : '5725a680b3249760ea21de52',
            name    : 'Aaron',
            lastName: 'Singh',
            avatar  : 'assets/images/avatars/Abbott.jpg',
            department: 'BE/IS/2020',
            risklevelInsta: 5,
            accuracyInsta: 90.4,
            risklevelReddit: 4,
            accuracyReddit: 89.7,
            risklevelFB: 4,
            accuracyFB: 89.8,
            risklevelTwitter: 5,
            accuracyTwitter: 98.4
        },
        {
            id      : '5725a680606588342058356d',
            name    : 'Arnold',
            lastName: 'Matlock',
            avatar  : 'assets/images/avatars/Arnold.jpg',
            department: 'BE/IS/2020',
            risklevelInsta: 2,
            accuracyInsta: 90.1,
            risklevelReddit: 1,
            accuracyReddit: 83.7,
            risklevelFB: 2,
            accuracyFB: 83.8,
            risklevelTwitter: 3,
            accuracyTwitter: 94.4

        },
        {
            id      : '5725a68009e20d0a9e9acf2a',
            name    : 'Sherine',
            lastName: 'Bradbury',
            avatar  : 'assets/images/avatars/Barrera.jpg',
            department: 'BE/IS/2020',
            risklevelInsta: 5,
            accuracyInsta: 90.4,
            risklevelReddit: 4,
            accuracyReddit: 89.7,
            risklevelFB: 4,
            accuracyFB: 89.8,
            risklevelTwitter: 5,
            accuracyTwitter: 98.4
        },
        {
            id      : '5725a6809fdd915739187ed5',
            name    : 'Light',
            lastName: 'Yagami',
            avatar  : 'assets/images/avatars/Blair.jpg',
            department: 'BE/IS/2020',
            risklevelInsta: 5,
            accuracyInsta: 90.4,
            risklevelReddit: 4,
            accuracyReddit: 89.7,
            risklevelFB: 4,
            accuracyFB: 89.8,
            risklevelTwitter: 5,
            accuracyTwitter: 98.4
        },

    ];

    public static user = [
        {
            id              : '5725a6802d10e277a0f35724',
            name            : 'John Doe',
            avatar          : 'assets/images/avatars/profile.jpg',
            starred         : [
                '5725a680ae1ae9a3c960d487',
                '5725a6801146cce777df2a08',
                '5725a680bbcec3cc32a8488a',
                '5725a680bc670af746c435e2',
                '5725a68009e20d0a9e9acf2a'
            ],
            frequentContacts: [
                '5725a6809fdd915739187ed5',
                '5725a68031fdbb1db2c1af47',
                '5725a680606588342058356d',
                '5725a680e7eb988a58ddf303',
                '5725a6806acf030f9341e925',
                '5725a68034cb3968e1f79eac',
                '5725a6801146cce777df2a08',
                '5725a680653c265f5c79b5a9'
            ],
            groups          : [
                {
                    id        : '5725a6802d10e277a0f35739',
                    name      : 'Friends',
                    contactIds: [
                        '5725a680bbcec3cc32a8488a',
                        '5725a680e87cb319bd9bd673',
                        '5725a6802d10e277a0f35775'
                    ]
                },
                {
                    id        : '5725a6802d10e277a0f35749',
                    name      : 'Clients',
                    contactIds: [
                        '5725a680cd7efa56a45aea5d',
                        '5725a68018c663044be49cbf',
                        '5725a6809413bf8a0a5272b1',
                        '5725a6803d87f1b77e17b62b'
                    ]
                },
                {
                    id        : '5725a6802d10e277a0f35329',
                    name      : 'Recent Workers',
                    contactIds: [
                        '5725a680bbcec3cc32a8488a',
                        '5725a680653c265f5c79b5a9',
                        '5725a6808a178bfd034d6ecf',
                        '5725a6801146cce777df2a08'
                    ]
                }
            ]
        }
    ];
}
