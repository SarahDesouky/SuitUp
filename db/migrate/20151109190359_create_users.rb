class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :fname
      t.string :lname
      t.date :date_of_birth
      t.string :email
      t.string :password
      t.string :avatar_url
      t.string :gender
      t.string :country

      t.timestamps
    end
  end
end
