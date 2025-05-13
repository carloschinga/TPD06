package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DetalleServlet")
public class DetalleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String codiDeta = request.getParameter("codiDeta");
        String codiVent = request.getParameter("codiVent");
        String codiProd = request.getParameter("codiProd");
        int cantDeta = Integer.parseInt(request.getParameter("cantDeta"));
        double preecProd = Double.parseDouble(request.getParameter("preecProd"));
        double sbttDeta = cantDeta * preecProd;

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Detalle Procesado</title></head><body>");
        out.println("<h2>Resultado del Detalle</h2>");
        out.println("<table border='1' cellpadding='5'>");
        out.println("<tr><th>Campo</th><th>Valor</th></tr>");
        out.println("<tr><td>Código Detalle</td><td>" + codiDeta + "</td></tr>");
        out.println("<tr><td>Código Venta</td><td>" + codiVent + "</td></tr>");
        out.println("<tr><td>Código Producto</td><td>" + codiProd + "</td></tr>");
        out.println("<tr><td>Cantidad</td><td>" + cantDeta + "</td></tr>");
        out.println("<tr><td>Precio Producto</td><td>" + preecProd + "</td></tr>");
        out.println("<tr><td>Subtotal</td><td>" + sbttDeta + "</td></tr>");
        out.println("</table>");
        out.println("<br><a href='detalle.html'>← Volver</a>");
        out.println("</body></html>");
    }
}
