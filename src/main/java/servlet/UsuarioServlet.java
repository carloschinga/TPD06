/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dto.Usuario;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author USER
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuario"})
public class UsuarioServlet extends HttpServlet {

    @PersistenceUnit(unitName = "com.mycompany_TPD06_war_1.0-SNAPSHOTPU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_TPD06_war_1.0-SNAPSHOTPU");

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = getEntityManager();
        try {
            List<Usuario> usuarios = em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Usuario c : usuarios) {
                JSONObject obj = new JSONObject();
                obj.put("codiUsua", c.getCodiUsua());
                obj.put("logiUsua", c.getLogiUsua());
                jsonArray.put(obj);
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonArray.toString());
            out.flush();
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        EntityManager em = getEntityManager();
        try {
            BufferedReader reader = request.getReader();
            JSONObject json = new JSONObject(reader.lines().collect(Collectors.joining()));

            Usuario u = new Usuario();
            u.setCodiUsua(json.getInt("codiUsua"));
            u.setLogiUsua(json.getString("logiUsua"));
            u.setPassUsua(json.getString("passUsua"));

            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();

            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Usuario creado\"}");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        EntityManager em = getEntityManager();
        try {
            BufferedReader reader = request.getReader();
            JSONObject json = new JSONObject(reader.lines().collect(Collectors.joining()));

            int codiUsua = json.getInt("codiUsua");

            Usuario u = em.find(Usuario.class, codiUsua);
            if (u == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
                return;
            }

            em.getTransaction().begin();
            u.setLogiUsua(json.getString("logiUsua"));
            u.setPassUsua(json.getString("passUsua"));
            em.getTransaction().commit();

            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Usuario actualizado\"}");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        EntityManager em = getEntityManager();
        try {
            int codiUsua = Integer.parseInt(request.getParameter("codiUsua"));

            Usuario u = em.find(Usuario.class, codiUsua);
            if (u == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
                return;
            }

            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();

            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Usuario eliminado\"}");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } finally {
            em.close();
        }
    }

}
