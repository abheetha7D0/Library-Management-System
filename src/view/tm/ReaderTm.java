package view.tm;

public class ReaderTm {

        private String readerId;
        private String name;
        private int age;
        private String tp_No;
        private String address;

        public ReaderTm() {
        }

        public ReaderTm(String readerId, String name, int age, String tp_No, String address) {
            this.readerId = readerId;
            this.name = name;
            this.age = age;
            this.tp_No = tp_No;
            this.address = address;
        }

        public String getReaderId() {
            return readerId;
        }

        public void setReaderId(String readerId) {
            this.readerId = readerId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTp_No() {
            return tp_No;
        }

        public void setTp_No(String tp_No) {
            this.tp_No = tp_No;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }


}
