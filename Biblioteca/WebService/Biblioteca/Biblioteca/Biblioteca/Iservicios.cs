using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;
using System.Web.Script.Services;

namespace Biblioteca
{
    // NOTA: puede usar el comando "Rename" del menú "Refactorizar" para cambiar el nombre de interfaz "Iservicios" en el código y en el archivo de configuración a la vez.
    [ServiceContract]
    public interface Iservicios
    {
        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "ListarLibros", ResponseFormat = WebMessageFormat.Json)]
        List<libroClase> ListarLibros();
        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "AgregarLibros", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        bool AgregarLibros(libroClase li);
        [OperationContract]
        [WebInvoke(Method = "PUT", UriTemplate = "EditarLibros", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        bool EditarLibros(libroClase li);
        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "EliminarLibros/{run}", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        bool EliminarLibros(string run);
        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "BuscarLibros/{nombre}", ResponseFormat = WebMessageFormat.Json)]
        libroClase BuscarLibros(string nombre);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "ListarLibrosF", ResponseFormat = WebMessageFormat.Json)]
        List<librosFavoritosClase> ListarLibrosF();
        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "AgregarLibrosF", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        bool AgregarLibrosF(librosFavoritosClase li);
        [OperationContract]
        [WebInvoke(Method = "PUT", UriTemplate = "EditarLibrosF", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        bool EditarLibrosF(librosFavoritosClase li);
        [OperationContract]
        [WebInvoke(Method = "DELETE", UriTemplate = "EliminarLibrosF", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        bool EliminarLibrosF(librosFavoritosClase li);
        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "BuscarLibrosF/{nombre}", ResponseFormat = WebMessageFormat.Json)]
        librosFavoritosClase BuscarLibrosF(string nombre);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "ListarUsuarios", ResponseFormat = WebMessageFormat.Json)]
        List<usuarioClase> ListarUsuarios();
        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "AgregarUsuarios", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        bool AgregarUsuarios(usuarioClase x);
        [OperationContract]
        [WebInvoke(Method = "PUT", UriTemplate = "EditarUsuarios", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        bool EditarUsuarios(usuarioClase x);
        [OperationContract]
        [WebInvoke(Method = "DELETE", UriTemplate = "EliminarUsuarios", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        bool EliminarUsuarios(usuarioClase x);
        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "BuscarUsuarios/{run}", ResponseFormat = WebMessageFormat.Json)]
        usuarioClase BuscarUsuarios(string run);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "ListarPrestamos", ResponseFormat = WebMessageFormat.Json)]
        List<prestamoClase> ListarPrestamos();
        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "ListarPrestamosr/{run}", ResponseFormat = WebMessageFormat.Json)]
        List<prestamoClase> ListarPrestamosr(string run);
        [OperationContract]
        [WebInvoke(Method = "POST", UriTemplate = "AgregarPrestamos", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        bool AgregarPrestamos(prestamoClase x);
        [OperationContract]
        [WebInvoke(Method = "PUT", UriTemplate = "EditarPrestamos", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        bool EditarPrestamos(prestamoClase x);
        [OperationContract]
        [WebInvoke(Method = "DELETE", UriTemplate = "EliminarPrestamos", ResponseFormat = WebMessageFormat.Json, RequestFormat = WebMessageFormat.Json)]
        bool EliminarPrestamos(prestamoClase x);
        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "BuscarPrestamos/{run}", ResponseFormat = WebMessageFormat.Json)]
        prestamoClase BuscarPrestamos(string run);

        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "Stock/{codigo},{cantidad}", ResponseFormat = WebMessageFormat.Json)]
        bool Stock(string codigo, string cantidad);
        [OperationContract]
        [WebInvoke(Method = "GET", UriTemplate = "CambiarStock/{codigo},{cantidad}", ResponseFormat = WebMessageFormat.Json)]
        bool CambiarStock(string codigo, string cantidad);



    }
}
