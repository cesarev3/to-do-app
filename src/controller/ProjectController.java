package controller;

//import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
//import model.Task;
import util.ConnectionFactory;

public class ProjectController {
    public void save(Project project) {
        String sql = "INSERT INTO projects(name,"
                + " description,"
                + " createdAt,"
                + " updatedAt) VALUES (?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Cria uma conexão com o banco de dados
            connection = ConnectionFactory.getConnection();

            //Cria um PreparedStatement para executar a query
            statement = connection.prepareStatement(sql);


            statement.setString(1,project.getName());
            statement.setString(2,project.getDescription());
            statement.setDate(3, new java.sql.Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new java.sql.Date(project.getUpdatedAt().getTime()));

            //Executa a SQL de inserção de dados
            statement.execute();

        } catch (SQLException ex){
            throw new RuntimeException("ERRO ao salvar a tarefa");
        } finally {
            //Fecha as conexões
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
        }
    }

    public void update(Project project) {
        String sql = "UPDATE projects SET "
                + "name = ?, "
                + "description = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id = ?";


        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();

            //Preparando a Query
            statement = connection.prepareStatement(sql);

            //Setando os valores do statement
            statement.setString(1,project.getName());
            statement.setString(2,project.getDescription());
            statement.setDate(3, new java.sql.Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new java.sql.Date(project.getUpdatedAt().getTime()));
            statement.setInt(5,project.getId());

            //Executando a query
            statement.execute();

        } catch (SQLException ex){
            throw new RuntimeException("ERRO ao atualizar a tarefa");
        } finally {
            //Fecha as conexões
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
        }

    }

    //Todos os projetos serão listados, logo filtro não é necessário
    public List<Project> getAll(){

        String sql = "SELECT * FROM projects";

        //Lista de tarefas que será devolvida quando o método acontecer
        List<Project> projects = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        //Classe que vai recuperar os dados do banco de dados
        ResultSet resultSet = null;


        try {
            //Criando a conexão
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            //Não necessário >> Setando o valor que corresponde ao filtro de busca
            //statement.setInt(1,id);

            //Valor retornado pela execução da query
            resultSet = statement.executeQuery();

            //Enquanto houverem valores a serem percorridos no meu resultado
            while(resultSet.next()){
                Project project = new Project();

                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));

                projects.add(project);
            }


        } catch (SQLException ex){
            throw new RuntimeException("ERRO ao inserir a tarefa" + ex.getMessage(), ex);
        } finally {
            //Fecha as conexões
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
        }
        //Lista de tarefas que foi criada e carregada
        return projects;
    }


    public void removeByid(int id) {
        String sql = "DELETE FROM projects WHERE id =?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Criando a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();

            //Preparando a query
            statement = connection.prepareStatement(sql);

            //Setando os valores
            statement.setInt(1, id);

            //Executandoa query
            statement.execute();

        } catch (SQLException ex){
            throw new RuntimeException("ERRO ao deletar a tarefa" + ex.getMessage());
        } finally {
            //Fecha as conexões
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erro ao fechar a conexão", ex);
            }
        }
    }
}
