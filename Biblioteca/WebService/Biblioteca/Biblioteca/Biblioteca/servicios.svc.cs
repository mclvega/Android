using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Biblioteca
{
    // NOTA: puede usar el comando "Rename" del menú "Refactorizar" para cambiar el nombre de clase "servicios" en el código, en svc y en el archivo de configuración a la vez.
    // NOTA: para iniciar el Cliente de prueba WCF para probar este servicio, seleccione servicios.svc o servicios.svc.cs en el Explorador de soluciones e inicie la depuración.
    public class servicios : Iservicios
    {
        public bool AgregarLibros(libroClase li)
        {
            using (librosmclEntities lb = new librosmclEntities())
            {
                try
                {
                    libro x = new libro();
                    x.nombre = li.nombre;
                    x.stock = li.stock;
                    x.activo = li.activo;
                    lb.libro.Add(x);
                    lb.SaveChanges();
                    return true;
                }
                catch
                {
                    return false;
                }
            };
        }

        public bool AgregarLibrosF(librosFavoritosClase li)
        {
            using (librosmclEntities lb = new librosmclEntities())
            {
                try
                {
                    librosfavoritos x = new librosfavoritos();
                    x.usuario = li.usuario ;
                    x.libro = li.libro;
                    lb.librosfavoritos.Add(x);
                    lb.SaveChanges();
                    return true;
                }
                catch
                {
                    return false;
                }
            };
        }

        public bool AgregarPrestamos(prestamoClase p)
        {
            using (librosmclEntities lb = new librosmclEntities())
            {
                try
                {
                    prestamos x = new prestamos();
                    x.libro = p.libro;
                    x.usuario = p.usuario;
                    x.fecha_devolucion = p.fecha_devolucion;
                    x.fecha_prestamo = DateTime.Now;
                    Descontar(x.libro);
                    lb.prestamos.Add(x);
                    lb.SaveChanges();
                    return true;
                }
                catch
                {
                    return false;
                }
            };
        }

        public bool AgregarUsuarios(usuarioClase u)
        {
            using (librosmclEntities lb = new librosmclEntities())
            {
                try
                {
                    usuario x = new usuario();
                    x.run = u.run;
                    x.nombre = u.nombre;
                    x.user = u.user;
                    x.pass = u.pass;
                    x.activo = u.activo;
                    x.tipo = u.tipo;
                    lb.usuario.Add(x);
                    lb.SaveChanges();
                    return true;
                }
                catch
                {
                    return false;
                }
            };
        }

        public libroClase BuscarLibros(string nombre)
        {
            using (librosmclEntities lb = new librosmclEntities())
            {

                return lb.libro.Where(p => p.nombre == nombre).Select(s => new libroClase
                {
                    codigo=s.codigo,
                    nombre = s.nombre,
                    stock=s.stock,
                    activo=s.activo
                }).First();

            };
        }

        public librosFavoritosClase BuscarLibrosF(string nombre)
        {
            using (librosmclEntities lb = new librosmclEntities())
            {

                return lb.librosfavoritos.Where(p => p.libro1.nombre == nombre).Select(s => new librosFavoritosClase
                {
                    codigo = s.codigo,
                    libro=s.libro,
                    usuario=s.usuario
                }).First();

            };
        }

        public prestamoClase BuscarPrestamos(string run)
        {
            using (librosmclEntities lb = new librosmclEntities())
            {

                return lb.prestamos.Where(p => p.usuario1.run == run).Select(s => new prestamoClase
                {
                    codigo = s.codigo,
                    libro=s.libro,
                    fecha_devolucion=s.fecha_devolucion,
                    fecha_prestamo=s.fecha_prestamo,
                    usuario=s.usuario
                }).First();

            };
        }

        public usuarioClase BuscarUsuarios(string run)
        {
            using (librosmclEntities lb = new librosmclEntities())
            {

                return lb.usuario.Where(p => p.run == run).Select(s => new usuarioClase
                {
                    codigo = s.codigo,
                    run=s.run,
                    nombre = s.nombre,
                    user = s.user,
                    pass = s.pass,
                    activo=s.activo,
                    tipo=s.tipo
                }).First();

            };
        }
       

        public bool EditarLibros(libroClase li)
        {
            using (librosmclEntities lb = new librosmclEntities())
            {
                try
                {

                    libro x = lb.libro.Single(t => t.codigo == li.codigo);
                    x.nombre = li.nombre;
                    x.stock = li.stock;
                    x.activo = li.activo;
                    lb.SaveChanges();
                    return true;
                }
                catch
                {
                    return false;
                }
            };
        }

        public bool EditarLibrosF(librosFavoritosClase li)
        {
            throw new NotImplementedException();
        }

        public bool EditarPrestamos(prestamoClase x)
        {
            throw new NotImplementedException();
        }

        public bool EditarUsuarios(usuarioClase x)
        {
            throw new NotImplementedException();
        }

        public bool EliminarLibros(string codigo)
        {
            using (librosmclEntities lb = new librosmclEntities())
            {
                try
                {
                    libro x = lb.libro.Single(t => t.codigo == int.Parse(codigo));
                    lb.libro.Remove(x);
                    lb.SaveChanges();
                    return true;
                }
                catch
                {
                    return false;
                }
            };
        }

        public bool EliminarLibrosF(librosFavoritosClase li)
        {
            throw new NotImplementedException();
        }

        public bool EliminarPrestamos(prestamoClase x)
        {
            throw new NotImplementedException();
        }

        public bool EliminarUsuarios(usuarioClase x)
        {
            throw new NotImplementedException();
        }

        public List<libroClase> ListarLibros()
        {
            using (librosmclEntities lb = new librosmclEntities())
            {


                return lb.libro.Where(p=>p.activo=="1").Select(s => new libroClase
                {
                    codigo = s.codigo,
                    nombre = s.nombre,
                    stock=s.stock,
                    activo=s.activo
                }).ToList();
            };
        }

        public List<librosFavoritosClase> ListarLibrosF()
        {
            using (librosmclEntities lb = new librosmclEntities())
            {


                return lb.librosfavoritos.Select(s => new librosFavoritosClase
                {
                    codigo = s.codigo,
                    usuario=s.usuario,
                    libro=s.libro
                }).ToList();
            };
        }

        public List<prestamoClase> ListarPrestamos()
        {
            using (librosmclEntities lb = new librosmclEntities())
            {


                return lb.prestamos.Select(s => new prestamoClase
                {
                    codigo = s.codigo,
                    libro = s.libro,
                    fecha_devolucion = s.fecha_devolucion,
                    fecha_prestamo = s.fecha_prestamo,
                    usuario=s.usuario,
                    run=s.usuario1.run,
                    nombre=s.usuario1.nombre,
                    nombre_libro=s.libro1.nombre
                }).ToList();
            };
        }
        public List<prestamoClase> ListarPrestamosr(string run)
        {
            using (librosmclEntities lb = new librosmclEntities())
            {


                return lb.prestamos.Where(r=>r.usuario1.run==run).Select(s => new prestamoClase
                {
                    codigo = s.codigo,
                    libro = s.libro,
                    fecha_devolucion = s.fecha_devolucion,
                    fecha_prestamo = s.fecha_prestamo,
                    usuario = s.usuario,
                    run = s.usuario1.run,
                    nombre = s.usuario1.nombre,
                    nombre_libro = s.libro1.nombre
                }).ToList();
            };
        }

        public List<usuarioClase> ListarUsuarios()
        {

            using (librosmclEntities lb = new librosmclEntities())
            {


                return lb.usuario.Select(s => new usuarioClase
                {
                    codigo = s.codigo,
                    run=s.run,
                    nombre=s.nombre,
                    user=s.user,
                    pass=s.pass,
                    tipo=s.tipo
                }).ToList();
            };
        }



        public bool Stock(string nombre, string cantidad)
        {
            using (librosmclEntities tipoPro = new librosmclEntities())
            {
                try
                {
                    decimal cant = decimal.Parse(cantidad);
                    libro ti = tipoPro.libro.Single(t => t.nombre == nombre);
                    decimal s = int.Parse(ti.stock) - cant;
                    if (s >= 1)
                    {
                        ti.stock = s.ToString();
                        tipoPro.SaveChanges();
                        return true;
                    }
                    else if (s <= 0)
                    {
                        ti.stock = "0";
                        tipoPro.SaveChanges();
                        Desactivar(nombre);
                        return true;

                    }
                    return false;
                }
                catch
                {
                    return false;
                }
            };
        }
        public bool CambiarStock(string codigo, string cantidad)
        {
            using (librosmclEntities tipoPro = new librosmclEntities())
            {
                try
                {
                    decimal c = decimal.Parse(codigo);
                    decimal cant = decimal.Parse(cantidad);
                    libro ti = tipoPro.libro.Single(t => t.codigo == c);
                    
                    if (cant >= 1)
                    {
						
                        ti.stock = cant.ToString();
                        ti.activo = "1";
                        tipoPro.SaveChanges();
                        return true;
                    }
                    else if (cant <= 0)
                    {
                        ti.stock = "0";
                        tipoPro.SaveChanges();
                        Desactivar(codigo);
                        return true;

                    }
                    return false;
                }
                catch
                {
                    return false;
                }
            };
        }
        private bool Desactivar(string codigo)
        {
            using (librosmclEntities tipoPro = new librosmclEntities())
            {
                try
                {
                    int c = int.Parse(codigo);
                    libro ti = tipoPro.libro.Single(t => t.codigo == c);
                    ti.activo = "0";
                    tipoPro.SaveChanges();
                    return true;
                }
                catch
                {
                    return false;
                }
            };
        }
        private bool Descontar(int codigo)
        {
            using (librosmclEntities tipoPro = new librosmclEntities())
            {
                try
                {
                    
                    libro ti = tipoPro.libro.Single(t => t.codigo == codigo);
                    string s = (int.Parse(ti.stock) - 1).ToString();
                    ti.stock=s;
                    tipoPro.SaveChanges();
                    return true;
                }
                catch
                {
                    return false;
                }
            };
        }
    }
}
