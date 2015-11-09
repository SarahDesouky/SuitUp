class CreateMessagesReceiveds < ActiveRecord::Migration
  def change
    create_table :messages_receiveds do |t|
      t.references :message, index: true
      t.references :user, index: true

      t.timestamps
    end
  end
end
