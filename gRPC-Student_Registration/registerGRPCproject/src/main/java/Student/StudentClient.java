package Student;

import com.project.grpc.register.Student;
import com.project.grpc.register.studentGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class StudentClient {

    public static void main(String[] args) throws IOException {
        // Connection established with server
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 5050).usePlaintext().build();
        // Synchronous communication
        studentGrpc.studentBlockingStub studentBlockingStub = new studentGrpc.studentBlockingStub(managedChannel);

        //Login
        boolean auth = false;
        BufferedReader br = new BufferedReader((new InputStreamReader(System.in)));
        while(!auth){
            System.out.print("Enter user name: ");
            String name = br.readLine();
            System.out.print("Enter password: ");
            String pass = br.readLine();
            Student.LoginRequest loginRequest = Student.LoginRequest.newBuilder().
                    setUserName(name).setPassword(pass).build();
            Student.Response response = studentBlockingStub.login(loginRequest);
            if(response.getResponseCode() == 200) {
                auth = true;
            }
            System.out.println(response.getResponse());
        }

        //Registration
        System.out.print("Enter registration ID: ");
        long ID = Integer.parseInt(br.readLine());
        System.out.print("Enter student name: ");
        String name = br.readLine();



        Student.RegisterRequest registerRequest = Student.RegisterRequest.newBuilder().
                setRegistrationID(ID).setStudentName(name).build();
        Student.RegResponse regResponse = studentBlockingStub.register(registerRequest);
        System.out.println(regResponse.getResponse());
    }
}
