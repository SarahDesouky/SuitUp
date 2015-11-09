class CreateMessages < ActiveRecord::Migration
  def change
    create_table :messages do |t|
      t.references :owner, index: true
      t.string :text
      t.references :thread, index: true

      t.timestamps
    end
  end
end
