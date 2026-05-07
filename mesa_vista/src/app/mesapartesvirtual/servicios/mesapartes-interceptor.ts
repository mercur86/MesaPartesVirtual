import { HttpInterceptorFn } from '@angular/common/http';

export const mesapartesInterceptor: HttpInterceptorFn = (req, next) => {
  const token = 'sk_7825.jyG4d88J0cttJU8q2gxstx1y2kqgc29E';
  
  // Endpoints a los que NO se debe agregar el token
  const publicEndpoints = [
    '/reniec',        // tus endpoints externos
    '/api/auth/login',
    '/api/auth/register',
    'api/public/'
  ];

  // Si la URL incluye alguno de los endpoints públicos, no agrega token
  const isPublic = publicEndpoints.some(endpoint => req.url.includes(endpoint));

  if (isPublic) {
    return next(req);
  }

  // Agregar token solo si no es un endpoint público
  const authReq = token
    ? req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      })
    : req;

  return next(authReq);
};
