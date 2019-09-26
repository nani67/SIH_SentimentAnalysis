export class ClassroomMonFakeDb
{
    public static courses = [
        {
            dept: 'BE/IS/2020',
            studentCount: 3,
            name: 'Year 4 - Section A',
            currentSub: 'Android App Dev',
        },
        {
            dept: 'BE/IS/2020',
            studentCount: 15,
            name: 'Year 4 - Section B',
            currentSub: 'Finite Automata',
        },
        {
            dept: 'BE/IS/2020',
            studentCount: 10,
            name: 'Year 4 - Section C',
            currentSub: 'Open Elective',
        }

    ];

    public static categories = [
        {
            id   : 0,
            value: 'web',
            label: 'Web'
        },
        {
            id   : 1,
            value: 'firebase',
            label: 'Firebase'
        },
        {
            id   : 2,
            value: 'cloud',
            label: 'Cloud'
        },
        {
            id   : 3,
            value: 'android',
            label: 'Android'
        }
    ];


}
