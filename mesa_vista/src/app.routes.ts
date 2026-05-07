import { Routes } from '@angular/router';
import { Mesapartes } from '@/mesapartesvirtual/mesapartes/mesapartes';
import { Personanatural } from '@/mesapartesvirtual/paginas/personanatural/personanatural';
import { Personanajuridica } from '@/mesapartesvirtual/paginas/personanajuridica/personanajuridica';
import { Empty } from '@/pages/empty/empty';

export const appRoutes: Routes = [
    {
        path: '',
        component: Mesapartes

    },

    { path: 'mesapartes', component: Mesapartes },
    { path: 'mesapartes/personanatural', component: Personanatural },
    { path: 'mesapartes/personajuridica', component: Personanajuridica },

    { path: 'notfound', component: Empty },

    { path: '**', redirectTo: '/notfound' }
];
