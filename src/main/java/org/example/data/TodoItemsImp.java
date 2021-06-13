 package org.example.data;
import org.example.model.Person;
import org.example.model.Todo;
import org.example.mySqlConnection.MySqlConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class TodoItemsImp extends TodoItems {
    private static final int RETURN_GENERATED_KEYS = 1;

    @Override
        public Todo create(Todo todo) {
            String query = "INSERT INTO todo_item VALUE (0,?,?,?,?,?);";
            try (
                    PreparedStatement preparedStatement = MySqlConnection.getConnection().prepareStatement(query, RETURN_GENERATED_KEYS)
            ) {
                preparedStatement.setString(1, todo.getTitle());
                preparedStatement.setString(2, todo.getDescription());
                preparedStatement.setDate(3, Date.valueOf(todo.getDeadline()));
                preparedStatement.setBoolean(4, todo.isDone());
                preparedStatement.setInt(5, todo.getAssigneeId());

                preparedStatement.executeUpdate();
                ResultSet genKeys = preparedStatement.getGeneratedKeys();
                if (genKeys.next()) {
                    todo.setTodoId(genKeys.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return todo;
        }

        @Override
        public Collection<Todo> findAll() {
            String query = "Select * from todo_item;";
            Collection<Todo> todoList = new ArrayList<>();
            try {
                Statement statement = MySqlConnection.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) todoList.add(new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6)));

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return todoList;
        }

        @Override
        public Todo findById(int id) {
            String query = "SELECT * from todo_item where todo_id = ?;";
            Todo todo = null;
            try (
                    PreparedStatement preparedStatement = MySqlConnection.getConnection().prepareStatement(query)
            ) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    todo = new Todo(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDate(4).toLocalDate(),
                            resultSet.getBoolean(5),
                            resultSet.getInt(6));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return todo;
        }

        @Override
        public Collection<Todo> findByAssignee(int assigneeId) {
            String query = "SELECT * from todo_item where assignee_id = ?;";
            Collection<Todo> todoList = new ArrayList<>();
            try (
                    PreparedStatement preparedStatement = MySqlConnection.getConnection().prepareStatement(query)
            ) {
                preparedStatement.setInt(1, assigneeId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) todoList.add(new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6)));

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return todoList;
        }

        @Override
        public Collection<Todo> findByAssignee(Person assignee) {
            return findByAssignee(assignee.getPersonId());
        }

        @Override
        public Collection<Todo> findByDoneStatus(boolean done) {
            String query = "SELECT * from todo_item where done= ?;";
            Collection<Todo> todoList = new ArrayList<>();
            try (
                    PreparedStatement preparedStatement = MySqlConnection.getConnection().prepareStatement(query)
            ) {
                preparedStatement.setBoolean(1, done);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) todoList.add(new Todo(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getBoolean(5),
                        resultSet.getInt(6)));

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return todoList;
        }

        @Override
        public Collection<Todo> findByUnassignedTodoItems() {
            return findByAssignee(0);
        }

    public <todoToUpdate> Todo update(todoToUpdate todoToUpdate) {
           return update(todoToUpdate );
       }
//    public <todoToUpdate> Todo update(Todo todoToUpdate ) {
//            String query = "UPDATE todo_item  SET title=?,description=?,deadline=?,done=?,assignee_id=? WHERE todoid=?;";
//        //    org.example.model.Todo todoToUpdate = null;
//            try (
//                    PreparedStatement preparedStatement = MySqlConnection.getConnection().prepareStatement(query)
//            ) {
//                preparedStatement.setString(1, todoToUpdate.getTitle());
//                preparedStatement.setString(2, todoToUpdate.getDescription());
//                preparedStatement.setDate(3, Date.valueOf(query.valueOf(todoToUpdate)));
//                preparedStatement.setBoolean(4, todoToUpdate.isDone());
//                preparedStatement.setInt(5, todoToUpdate.getAssigneeId());
//                preparedStatement.setInt(6, todoToUpdate.getTodoId());
//                preparedStatement.executeUpdate();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//            return todoToUpdate;
//        }

        @Override
        public boolean deleteById(int id) {
            String query = "DELETE FROM todo_item WHERE todo_id=?";
            boolean result = false;
            if (findById(id) != null) {
                try (
                        PreparedStatement preparedStatement = MySqlConnection.getConnection().prepareStatement(query)
                ) {
                    preparedStatement.setInt(1, id);
                    result = preparedStatement.executeUpdate()==0;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return result;
        }
}
