//------------------------------------------------------------------------------
// <auto-generated>
//     Este código se generó a partir de una plantilla.
//
//     Los cambios manuales en este archivo pueden causar un comportamiento inesperado de la aplicación.
//     Los cambios manuales en este archivo se sobrescribirán si se regenera el código.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Biblioteca
{
    using System;
    using System.Collections.Generic;
    
    public partial class librosfavoritos
    {
        public int codigo { get; set; }
        public int libro { get; set; }
        public int usuario { get; set; }
    
        public virtual libro libro1 { get; set; }
        public virtual usuario usuario1 { get; set; }
    }
}
