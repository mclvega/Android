using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Biblioteca
{
    public class prestamoClase
    {
        public int codigo { get; set; }
        public int usuario { get; set; }
        public int libro { get; set; }
        public string run;
        public string nombre;
        public string nombre_libro;
        public System.DateTime fecha_prestamo { get; set; }
        public System.DateTime fecha_devolucion { get; set; }
    }
}